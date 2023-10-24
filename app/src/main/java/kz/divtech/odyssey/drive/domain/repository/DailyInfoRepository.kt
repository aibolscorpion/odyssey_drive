package kz.divtech.odyssey.drive.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.main.DailyInfo

interface DailyInfoRepository {
    suspend fun getDailyInfo(): Flow<Resource<DailyInfo>>
}