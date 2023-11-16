package kz.divtech.odyssey.drive.domain.use_case.main

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.domain.repository.TaskRepository
import kz.divtech.odyssey.drive.domain.use_case.BaseUseCase
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(private val repository: TaskRepository)
    :BaseUseCase<Int, Flow<Resource<Task>>>{
    override suspend fun execute(input: Int): Flow<Resource<Task>> {
        return repository.getTaskById(input)
    }
}