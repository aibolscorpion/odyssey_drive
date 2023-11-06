package kz.divtech.odyssey.drive.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.auth.toLoginResponse
import kz.divtech.odyssey.drive.data.dto.main.BadRequest
import kz.divtech.odyssey.drive.data.local.DataStoreManager
import kz.divtech.odyssey.drive.data.local.DataStoreManager.Companion.TOKEN_KEY
import kz.divtech.odyssey.drive.data.remote.ApiService
import kz.divtech.odyssey.drive.domain.repository.AuthRepository
import kz.divtech.odyssey.drive.domain.model.auth.LoginRequest
import kz.divtech.odyssey.drive.domain.model.auth.LoginResponse
import okhttp3.internal.http.HTTP_BAD_REQUEST
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dataStoreManager: DataStoreManager
): AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = api.login(loginRequest).toLoginResponse()
                dataStoreManager.saveData(TOKEN_KEY, response.accessToken)
                emit(Resource.Success(response))
            }catch (e: IOException){
                emit(Resource.Error("${e.message}"))
            }catch (e: HttpException){
                if(e.code() == HTTP_BAD_REQUEST){
                    val errorResponse = Gson().fromJson(e.response()?.errorBody()?.string(), BadRequest::class.java)
                    emit(Resource.Error(message = errorResponse.message))
                }else{
                    emit(Resource.Error("${e.message}"))
                }
            }
        }

    }

    override suspend fun logout(): Flow<Resource<Boolean>> {
        return flow {
            try {
                emit(Resource.Loading())
                dataStoreManager.saveData(TOKEN_KEY, "")
                emit(Resource.Success(true))
            }catch (e: IOException){
                emit(Resource.Error("${e.message}"))
            }catch (e: HttpException){
                emit(Resource.Error("${e.message}"))
            }
        }
    }
}