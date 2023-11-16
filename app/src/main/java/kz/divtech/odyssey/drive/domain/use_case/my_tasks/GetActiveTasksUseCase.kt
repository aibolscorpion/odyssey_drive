package kz.divtech.odyssey.drive.domain.use_case.my_tasks

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.domain.repository.TaskRepository
import kz.divtech.odyssey.drive.domain.use_case.BaseUseCase
import java.time.LocalDate
import javax.inject.Inject

class GetActiveTasksUseCase @Inject constructor(private val repository: TaskRepository)
    : BaseUseCase<LocalDate, Flow<PagingData<Task>>> {

    override suspend fun execute(input: LocalDate): Flow<PagingData<Task>> {
        return repository.getActiveTasks(input)
    }

}