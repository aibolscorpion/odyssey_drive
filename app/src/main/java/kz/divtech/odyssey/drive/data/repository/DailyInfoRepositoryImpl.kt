package kz.divtech.odyssey.drive.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.main.toDailyInfo
import kz.divtech.odyssey.drive.data.remote.ApiService
import kz.divtech.odyssey.drive.domain.model.main.DailyInfo
import kz.divtech.odyssey.drive.domain.repository.DailyInfoRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DailyInfoRepositoryImpl @Inject constructor(private val api: ApiService): DailyInfoRepository {

    override suspend fun getDailyInfo(): Flow<Resource<DailyInfo>> {
        return flow{
            try {
                emit(Resource.Loading())
                val response = api.getDailyInfo().toDailyInfo()
                emit(Resource.Success(data = response))
            }catch (e: IOException){
                emit(Resource.Error(message = "${e.message}"))
            }catch (e: HttpException){
                emit(Resource.Error(message = "${e.message}"))
            }
        }
    }
}