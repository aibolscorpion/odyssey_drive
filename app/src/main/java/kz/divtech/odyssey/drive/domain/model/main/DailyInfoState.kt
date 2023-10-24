package kz.divtech.odyssey.drive.domain.model.main


class DailyInfoState(
     val isLoading: Boolean = false,
     val error: String = "",
     val dailyInfo : DailyInfo = emptyDailyInfo
)


val emptyDailyInfo: DailyInfo =
    DailyInfo(0, 0, 0, null)