package kz.divtech.odyssey.drive.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.login.toLoginResponse
import kz.divtech.odyssey.drive.data.local.DataStoreManager
import kz.divtech.odyssey.drive.data.local.DataStoreManager.Companion.TOKEN_KEY
import kz.divtech.odyssey.drive.data.remote.ApiService
import kz.divtech.odyssey.drive.domain.repository.AuthRepository
import kz.divtech.odyssey.drive.domain.model.login.LoginRequest
import kz.divtech.odyssey.drive.domain.model.login.LoginResponse
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
                emit(Resource.Error("${e.message}"))
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