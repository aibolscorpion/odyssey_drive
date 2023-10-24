package kz.divtech.odyssey.drive.domain.model.main

class ShiftState(
    val isLoading: Boolean = false,
    val error: String = "",
    val status: ShiftStatus = ShiftStatus.OFFLINE
)

enum class ShiftStatus{
    OFFLINE, ONLINE, ON_BREAK
}