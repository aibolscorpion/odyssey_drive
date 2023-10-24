package kz.divtech.odyssey.drive.domain.use_case.my_tasks

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.domain.repository.TaskRepository
import javax.inject.Inject

class GetArchiveTasksUseCase @Inject constructor(val repository: TaskRepository) {

    suspend fun getArchiveTasks(): Flow<Resource<List<Task>>> {
        return repository.getArchiveTasks()
    }
}