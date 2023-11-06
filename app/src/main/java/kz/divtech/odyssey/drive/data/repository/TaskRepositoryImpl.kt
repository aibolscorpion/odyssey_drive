package kz.divtech.odyssey.drive.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.divtech.odyssey.drive.common.Constants.TASK_PAGE_SIZE
import kz.divtech.odyssey.drive.common.DateTimeUtils.formatToStringDate
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.main.BadRequest
import kz.divtech.odyssey.drive.data.dto.task_detail.TaskActionResponse
import kz.divtech.odyssey.drive.data.dto.task_detail.passengers.toPassengerList
import kz.divtech.odyssey.drive.data.dto.task_detail.toTask
import kz.divtech.odyssey.drive.data.paging_source.ActiveTaskPagingSource
import kz.divtech.odyssey.drive.data.paging_source.ArchiveTaskPagingSource
import kz.divtech.odyssey.drive.data.remote.ApiService
import kz.divtech.odyssey.drive.domain.model.task_detail.Passenger
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.domain.repository.TaskRepository
import okhttp3.internal.http.HTTP_BAD_REQUEST
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val api: ApiService): TaskRepository {
    override suspend fun getTaskById(id: Int): Flow<Resource<Task>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = api.getTaskById(id).toTask()
                emit(Resource.Success(data = response))
            }catch (e: IOException){
                emit(Resource.Error("${e.message}"))
            }catch (e: HttpException){
                emit(Resource.Error("${e.message}"))
            }
        }
    }

    override suspend fun getActiveTasks(date: LocalDate): Flow<PagingData<Task>> {
        return Pager(
            config = PagingConfig(pageSize = TASK_PAGE_SIZE),
            pagingSourceFactory = {
                ActiveTaskPagingSource(api = api, date.formatToStringDate())
            }
        ).flow
    }

    override suspend fun getArchiveTasks(): Flow<PagingData<Task>> {
        return Pager(
            config = PagingConfig(pageSize = TASK_PAGE_SIZE),
            pagingSourceFactory = {
                ArchiveTaskPagingSource(api = api)
            }
        ).flow
    }

    override suspend fun getPassengerList(taskId: Int): Flow<Resource<List<Passenger>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = api.getPassengerListById(taskId).toPassengerList()
                emit(Resource.Success(data = response))
            }catch (e: IOException){
                emit(Resource.Error("${e.message}"))
            }catch (e: HttpException){
                emit(Resource.Error("${e.message}"))
            }
        }
    }

    override suspend fun beginTask(taskId: Int): Flow<Resource<TaskActionResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = api.beginTaskById(taskId)
                emit(Resource.Success(data = response))
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
    override suspend fun completeTask(taskId: Int): Flow<Resource<TaskActionResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = api.completeTaskById(taskId)
                emit(Resource.Success(data = response))
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
    override suspend fun rejectTask(taskId: Int, comment: String): Flow<Resource<TaskActionResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = api.rejectTaskById(taskId, mapOf("comment" to comment))
                emit(Resource.Success(data = response))
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