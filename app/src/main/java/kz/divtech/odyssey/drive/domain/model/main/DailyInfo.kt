package kz.divtech.odyssey.drive.domain.model.main


data class DailyInfo(
    val taskCount: Int,
    val completedTaskCount: Int,
    val cancelledTaskCount: Int,
    val nearestTask: Task?
)

fun String.toShiftStatus(): ShiftStatus{
    ShiftStatus.entries.forEach{ state ->
        if(state.name.lowercase() == this) return state
    }
    return ShiftStatus.OFFLINE
}