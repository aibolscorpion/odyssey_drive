package kz.divtech.odyssey.drive.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.main.BadRequest
import kz.divtech.odyssey.drive.data.dto.main.SuccessResponse
import kz.divtech.odyssey.drive.data.dto.main.toShiftTime
import kz.divtech.odyssey.drive.data.remote.ApiService
import kz.divtech.odyssey.drive.domain.model.main.ShiftTime
import kz.divtech.odyssey.drive.domain.repository.ShiftRepository
import okhttp3.internal.http.HTTP_BAD_REQUEST
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShiftRepositoryImpl @Inject constructor(private val api: ApiService) : ShiftRepository {

    override suspend fun activate(): Flow<Resource<SuccessResponse>> =
        shiftRequest { api.activateShift() }

    override suspend fun pause(): Flow<Resource<SuccessResponse>> =
        shiftRequest { api.pauseShift() }

    override suspend fun resume(): Flow<Resource<SuccessResponse>> =
        shiftRequest { api.resumeShift() }

    override suspend fun deactivate(): Flow<Resource<SuccessResponse>> =
        shiftRequest { api.deactivateShift() }

    override suspend fun getShiftStartedTime(): Flow<Resource<ShiftTime>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = api.getShiftTime().toShiftTime()
                emit(Resource.Success(response))
            }catch (e: IOException){
                emit(Resource.Error("${e.message}"))
            }catch (e: HttpException){
                emit(Resource.Error("${e.message}"))
            }
        }
    }


    private fun shiftRequest(shiftRequest : suspend () -> SuccessResponse): Flow<Resource<SuccessResponse>>{
        return flow {
            try {
                emit(Resource.Loading())
                val response = shiftRequest()
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
}