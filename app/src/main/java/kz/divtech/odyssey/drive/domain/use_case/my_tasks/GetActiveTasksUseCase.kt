package kz.divtech.odyssey.drive.domain.use_case.my_tasks

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.domain.repository.TaskRepository
import java.time.LocalDate
import javax.inject.Inject

class GetActiveTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend fun getActiveTasks(date: LocalDate): Flow<PagingData<Task>> {
        return repository.getActiveTasks(date)
    }

}