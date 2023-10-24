package kz.divtech.odyssey.drive.domain.model.main


class ShiftTimeState(
    val isLoading: Boolean = false,
    val error: String = "",
    val shiftTime: ShiftTime = ShiftTime(ShiftStatus.OFFLINE, shiftRoutineStartTime = null)
)