package kz.divtech.odyssey.drive.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.main.SuccessResponse
import kz.divtech.odyssey.drive.domain.model.main.ShiftTime

interface ShiftRepository {
    suspend fun activate(): Flow<Resource<SuccessResponse>>
    suspend fun pause(): Flow<Resource<SuccessResponse>>
    suspend fun resume(): Flow<Resource<SuccessResponse>>
    suspend fun deactivate(): Flow<Resource<SuccessResponse>>
    suspend fun getShiftStartedTime(): Flow<Resource<ShiftTime>>

}