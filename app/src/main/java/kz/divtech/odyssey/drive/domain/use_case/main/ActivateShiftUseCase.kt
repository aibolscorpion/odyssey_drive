package kz.divtech.odyssey.drive.domain.use_case.main

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.main.SuccessResponse
import kz.divtech.odyssey.drive.domain.repository.ShiftRepository
import kz.divtech.odyssey.drive.domain.use_case.BaseUseCase
import javax.inject.Inject

class ActivateShiftUseCase @Inject constructor(private val repository: ShiftRepository)
    :BaseUseCase<Unit, Flow<Resource<SuccessResponse>>>{

    override suspend fun execute(input: Unit): Flow<Resource<SuccessResponse>> {
        return repository.activate()
    }

}