package kz.divtech.odyssey.drive.domain.use_case.main

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.main.ShiftTime
import kz.divtech.odyssey.drive.domain.repository.ShiftRepository
import javax.inject.Inject

class GetShiftStartedTimeUseCase @Inject constructor(private val repository: ShiftRepository) {

    suspend fun getShiftTime(): Flow<Resource<ShiftTime>> {
        return repository.getShiftStartedTime()
    }
    
}