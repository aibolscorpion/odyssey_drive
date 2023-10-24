package kz.divtech.odyssey.drive.domain.use_case.main

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.main.DailyInfo
import kz.divtech.odyssey.drive.domain.repository.DailyInfoRepository
import javax.inject.Inject

class GetDailyInfoUseCase @Inject constructor(private val repository: DailyInfoRepository) {
    suspend fun getDailyInfo(): Flow<Resource<DailyInfo>> {
        return repository.getDailyInfo()
    }
}