package kz.divtech.odyssey.drive.domain.use_case.auth

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.auth.LoginRequest
import kz.divtech.odyssey.drive.domain.model.auth.LoginResponse
import kz.divtech.odyssey.drive.domain.repository.AuthRepository
import kz.divtech.odyssey.drive.domain.use_case.BaseUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository):
    BaseUseCase<LoginRequest, Flow<Resource<LoginResponse>>> {

    override suspend fun execute(input: LoginRequest): Flow<Resource<LoginResponse>> {
        return repository.login(input)
    }
}