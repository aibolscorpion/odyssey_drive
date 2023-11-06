package kz.divtech.odyssey.drive.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.auth.LoginRequest
import kz.divtech.odyssey.drive.domain.model.auth.LoginResponse

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>
    suspend fun logout(): Flow<Resource<Boolean>>
}