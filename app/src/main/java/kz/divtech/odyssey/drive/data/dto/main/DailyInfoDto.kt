package kz.divtech.odyssey.drive.data.dto.main


import com.google.gson.annotations.SerializedName
import kz.divtech.odyssey.drive.data.dto.task_detail.TaskDto
import kz.divtech.odyssey.drive.data.dto.task_detail.toTask
import kz.divtech.odyssey.drive.domain.model.main.DailyInfo

data class DailyInfoDto(
    @SerializedName("active_task_count")
    val activeTaskCount: Int,
    @SerializedName("completed_task_count")
    val completedTaskCount: Int,
    @SerializedName("driver_daily_status")
    val driverDailyStatus: String,
    @SerializedName("nearest_task")
    val nearestTask: TaskDto?,
    @SerializedName("rejected_task_count")
    val rejectedTaskCount: Int,
    @SerializedName("shift_routine_start_time")
    val shiftRoutineStartTime: String?,
    @SerializedName("task_count")
    val taskCount: Int
)

fun DailyInfoDto.toDailyInfo(): DailyInfo{

    return DailyInfo(
        taskCount = taskCount,
        completedTaskCount = completedTaskCount,
        cancelledTaskCount = rejectedTaskCount,
        nearestTask = nearestTask?.toTask()
    )
}

