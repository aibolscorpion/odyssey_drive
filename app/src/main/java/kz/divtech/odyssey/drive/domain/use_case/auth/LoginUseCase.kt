package kz.divtech.odyssey.drive.domain.use_case.auth

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.auth.LoginRequest
import kz.divtech.odyssey.drive.domain.model.auth.LoginResponse
import kz.divtech.odyssey.drive.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> {
        return repository.login(loginRequest)
    }
}