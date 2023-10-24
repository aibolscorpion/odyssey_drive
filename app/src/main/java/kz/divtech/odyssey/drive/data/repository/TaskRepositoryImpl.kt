package kz.divtech.odyssey.drive.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.my_tasks.toTaskList
import kz.divtech.odyssey.drive.data.dto.task_detail.toTask
import kz.divtech.odyssey.drive.data.remote.ApiService
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.domain.repository.TaskRepository
import retrofit2.HttpException
import java.io.IOException
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

    override suspend fun getActiveTasks(): Flow<Resource<List<Task>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = api.getActiveTasks(1).toTaskList()
                emit(Resource.Success(data = response))
            }catch (e: IOException){
                emit(Resource.Error("${e.message}"))
            }catch (e: HttpException){
                emit(Resource.Error("${e.message}"))
            }
        }
    }

    override suspend fun getArchiveTasks(): Flow<Resource<List<Task>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = api.getArchiveTasks(1).toTaskList()
                emit(Resource.Success(data = response))
            }catch (e: IOException){
                emit(Resource.Error("${e.message}"))
            }catch (e: HttpException){
                emit(Resource.Error("${e.message}"))
            }
        }
    }


}