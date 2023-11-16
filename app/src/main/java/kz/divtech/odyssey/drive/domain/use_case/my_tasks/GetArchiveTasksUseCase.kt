package kz.divtech.odyssey.drive.domain.use_case.my_tasks

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.domain.repository.TaskRepository
import kz.divtech.odyssey.drive.domain.use_case.BaseUseCase
import javax.inject.Inject

class GetArchiveTasksUseCase @Inject constructor(val repository: TaskRepository)
    : BaseUseCase<Unit, Flow<PagingData<Task>>> {

    override suspend fun execute(input: Unit): Flow<PagingData<Task>> {
        return repository.getArchiveTasks()
    }
}