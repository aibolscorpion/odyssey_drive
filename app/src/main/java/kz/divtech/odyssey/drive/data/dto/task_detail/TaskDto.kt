package kz.divtech.odyssey.drive.data.dto.task_detail


import com.google.gson.annotations.SerializedName
import kz.divtech.odyssey.drive.common.DateTimeUtils.formatToDateWithText
import kz.divtech.odyssey.drive.common.DateTimeUtils.formatToTime
import kz.divtech.odyssey.drive.common.DateTimeUtils.isBtwTaskTime
import kz.divtech.odyssey.drive.common.DateTimeUtils.parseToLocalDateTime
import kz.divtech.odyssey.drive.data.dto.profile.CurrentVehicle
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.Status
import java.time.LocalDateTime

data class TaskDto(
    val id: Int,
    val status: String,
    @SerializedName("action_start_time")
    val actionStartTime: String,
    @SerializedName("action_end_time")
    val actionEndTime: String,
    @SerializedName("actual_start_time")
    val actualStartTime: String?,
    @SerializedName("actual_end_time")
    val actualEndTime: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("from_point")
    val fromPoint: FromPoint,
    @SerializedName("to_point")
    val toPoint: ToPoint,
    val notes: List<Any>,
    @SerializedName("passenger_count")
    val passengerCount: Int,
    @SerializedName("rejected_comment")
    val rejectedComment: String?,
    val vehicle: CurrentVehicle?,
    @SerializedName("rejected_time")
    val rejectedTime: String?
)

fun TaskDto.toTask(): Task{
    return Task(
        id = id,
        status = this.getTaskStatus(),
        passengerCount =  this.passengerCount,
        departureArrivalPlace = "${this.fromPoint.name} - ${this.toPoint.name}",
        date =  this.actionStartTime.formatToDateWithText(),
        time = "${this.actionStartTime.formatToTime()} - ${this.actionEndTime.formatToTime()}",
        actualStartTime = this.actualStartTime?.formatToTime() ?: "",
        actualEndTime = this.actualEndTime?.formatToTime() ?: "",
        seatCount = this.vehicle?.seatCount ?: 0,
        cancelReason = this.rejectedComment?: "",
        cancelTime = this.rejectedTime?.formatToTime()?: "",
        isCompletedInTime = this.actualEndTime?.parseToLocalDateTime()
            ?.isBtwTaskTime(this) ?: false
    )
}

fun TaskDto.getTaskStatus(): Status {
    return when(this.status){
        "active" -> {
            val now = LocalDateTime.now()
            when(now.isBtwTaskTime(this)){
                true -> Status.ON_THE_WAITING
                false -> Status.ASSIGNED
            }
        }
        "on_process" -> Status.IN_PROGRESS
        "completed" -> Status.COMPLETED
        "rejected" -> Status.CANCELLED
        else -> Status.COMPLETED
    }
}
