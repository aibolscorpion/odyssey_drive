package kz.divtech.odyssey.drive.domain.use_case.task_detail

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.task_detail.Passenger
import kz.divtech.odyssey.drive.domain.repository.TaskRepository
import javax.inject.Inject

class GetPassengersUseCase @Inject constructor(private val repository: TaskRepository) {

    suspend fun getPassengerList(taskId: Int): Flow<Resource<List<Passenger>>> {
        return repository.getPassengerList(taskId)
    }
}