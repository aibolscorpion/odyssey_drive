package kz.divtech.odyssey.drive.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.profile.toProfile
import kz.divtech.odyssey.drive.data.remote.ApiService
import kz.divtech.odyssey.drive.domain.model.profile.Profile
import kz.divtech.odyssey.drive.domain.repository.ProfileRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val api: ApiService): ProfileRepository {
    override suspend fun getProfile(): Flow<Resource<Profile>> {
        return flow{
            try {
                emit(Resource.Loading())
                val response = api.getProfile().toProfile()
                emit(Resource.Success(data = response))
            }catch (e: IOException){
                emit(Resource.Error(message = "${e.message}"))
            }catch (e: HttpException){
                emit(Resource.Error(message = "${e.message}"))
            }
        }
    }
}