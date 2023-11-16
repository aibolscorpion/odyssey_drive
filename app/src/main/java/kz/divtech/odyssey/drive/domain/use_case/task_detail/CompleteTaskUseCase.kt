package kz.divtech.odyssey.drive.domain.use_case.task_detail

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.task_detail.TaskActionResponse
import kz.divtech.odyssey.drive.domain.repository.TaskRepository
import kz.divtech.odyssey.drive.domain.use_case.BaseUseCase
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository)
    : BaseUseCase<Int, Flow<Resource<TaskActionResponse>>> {

    override suspend fun execute(input: Int): Flow<Resource<TaskActionResponse>> {
        return taskRepository.completeTask(input)
    }
}