package kz.divtech.odyssey.drive.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.task_detail.TaskActionResponse
import kz.divtech.odyssey.drive.domain.model.task_detail.Passenger
import kz.divtech.odyssey.drive.domain.model.main.Task
import java.time.LocalDate

interface TaskRepository {
    suspend fun getTaskById(id: Int): Flow<Resource<Task>>
    suspend fun getActiveTasks(date: LocalDate): Flow<PagingData<Task>>
    suspend fun getArchiveTasks(): Flow<PagingData<Task>>
    suspend fun getPassengerList(taskId: Int): Flow<Resource<List<Passenger>>>

    suspend fun beginTask(taskId: Int): Flow<Resource<TaskActionResponse>>
    suspend fun completeTask(taskId: Int): Flow<Resource<TaskActionResponse>>
    suspend fun rejectTask(taskId: Int, comment: String): Flow<Resource<TaskActionResponse>>

}