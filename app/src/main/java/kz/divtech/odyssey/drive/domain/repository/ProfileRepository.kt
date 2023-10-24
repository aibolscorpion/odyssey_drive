package kz.divtech.odyssey.drive.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.profile.Profile

interface ProfileRepository {
    suspend fun getProfile(): Flow<Resource<Profile>>
}