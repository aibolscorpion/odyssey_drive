package kz.divtech.odyssey.drive.domain.use_case.task_detail

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.task_detail.TaskActionResponse
import kz.divtech.odyssey.drive.domain.repository.TaskRepository
import javax.inject.Inject

class BeginTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend fun beginTask(taskId: Int): Flow<Resource<TaskActionResponse>> {
        return taskRepository.beginTask(taskId)
    }
}