package kz.divtech.odyssey.drive.domain.use_case.task_detail

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.task_detail.TaskActionResponse
import kz.divtech.odyssey.drive.domain.repository.TaskRepository
import kz.divtech.odyssey.drive.domain.use_case.BaseUseCase2Input
import javax.inject.Inject

class RejectTaskUseCase @Inject constructor(private val taskRepository: TaskRepository)
    :BaseUseCase2Input<Int, String, Flow<Resource<TaskActionResponse>>>{

    override suspend fun execute(input: Int, input2: String): Flow<Resource<TaskActionResponse>> {
        return taskRepository.rejectTask(input, input2)
    }
}