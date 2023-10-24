package kz.divtech.odyssey.drive.domain.use_case.auth

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun logout(): Flow<Resource<Boolean>> {
        return repository.logout()
    }
}