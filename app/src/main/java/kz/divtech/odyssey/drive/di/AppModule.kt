package kz.divtech.odyssey.drive.di
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.hilt.components.SingletonComponent

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kz.divtech.odyssey.drive.common.Constants
import kz.divtech.odyssey.drive.data.local.DataStoreManager
import kz.divtech.odyssey.drive.data.local.DataStoreManager.Companion.TOKEN_KEY
import kz.divtech.odyssey.drive.data.local.DataStoreManager.Companion.dataStore
import kz.divtech.odyssey.drive.data.remote.ApiService
import kz.divtech.odyssey.drive.data.repository.AuthRepositoryImpl
import kz.divtech.odyssey.drive.data.repository.DailyInfoRepositoryImpl
import kz.divtech.odyssey.drive.data.repository.ProfileRepositoryImpl
import kz.divtech.odyssey.drive.data.repository.ShiftRepositoryImpl
import kz.divtech.odyssey.drive.data.repository.TaskRepositoryImpl
import kz.divtech.odyssey.drive.domain.repository.AuthRepository
import kz.divtech.odyssey.drive.domain.repository.DailyInfoRepository
import kz.divtech.odyssey.drive.domain.repository.ProfileRepository
import kz.divtech.odyssey.drive.domain.repository.ShiftRepository
import kz.divtech.odyssey.drive.domain.repository.TaskRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.http.HTTP_UNAUTHORIZED
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferenceDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(dataStore: DataStore<Preferences>): DataStoreManager{
        return DataStoreManager(dataStore)
    }

    @Provides
    fun provideApiService(dataStoreManager: DataStoreManager): ApiService {
        val token = runBlocking { dataStoreManager.getData(TOKEN_KEY).firstOrNull() ?: ""}
         val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
             .addInterceptor(interceptor = { chain ->
                 val request: Request = chain.request().newBuilder()
                     .addHeader(Constants.AUTHORIZATION_KEY, "${Constants.AUTHORIZATION_VALUE_PREFIX} $token")
                     .build()
                 val response = chain.proceed(request)
                 if(response.code == HTTP_UNAUTHORIZED){
                     runBlocking {
                         dataStoreManager.saveData(TOKEN_KEY, "")
                     }
                 }
                 response
             }).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideLoginRepository(api: ApiService, dataStoreManager: DataStoreManager): AuthRepository {
        return AuthRepositoryImpl(api, dataStoreManager)

    }

    @Provides
    fun provideProfileRepository(api: ApiService): ProfileRepository {
        return ProfileRepositoryImpl(api)
    }

    @Provides
    fun provideDailyInfoRepository(api: ApiService): DailyInfoRepository{
        return DailyInfoRepositoryImpl(api)
    }

    @Provides
    fun provideShiftRepository(api: ApiService): ShiftRepository{
        return ShiftRepositoryImpl(api)
    }

    @Provides
    fun provideTaskRepository(api: ApiService): TaskRepository{
        return TaskRepositoryImpl(api)
    }


}