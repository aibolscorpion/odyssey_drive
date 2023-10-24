package kz.divtech.odyssey.drive.domain.use_case.main

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.main.SuccessResponse
import kz.divtech.odyssey.drive.domain.repository.ShiftRepository
import javax.inject.Inject

class DeactivateShiftUseCase @Inject constructor(private val repository: ShiftRepository) {

    suspend fun deactivateShift(): Flow<Resource<SuccessResponse>> {
        return repository.deactivate()
    }
    
}