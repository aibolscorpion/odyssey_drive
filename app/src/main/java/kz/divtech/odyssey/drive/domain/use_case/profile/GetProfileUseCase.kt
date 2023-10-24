package kz.divtech.odyssey.drive.domain.use_case.profile

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.profile.Profile
import kz.divtech.odyssey.drive.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: ProfileRepository) {

    suspend fun getProfile(): Flow<Resource<Profile>> {
        return repository.getProfile()
    }
}