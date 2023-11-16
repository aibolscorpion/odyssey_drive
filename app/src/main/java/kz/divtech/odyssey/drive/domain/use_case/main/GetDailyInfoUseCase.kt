package kz.divtech.odyssey.drive.domain.use_case.main

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.main.DailyInfo
import kz.divtech.odyssey.drive.domain.repository.DailyInfoRepository
import kz.divtech.odyssey.drive.domain.use_case.BaseUseCase
import javax.inject.Inject

class GetDailyInfoUseCase @Inject constructor(private val repository: DailyInfoRepository)
    :BaseUseCase<Unit, Flow<Resource<DailyInfo>>>{

    override suspend fun execute(input: Unit): Flow<Resource<DailyInfo>> {
        return repository.getDailyInfo()
    }
}