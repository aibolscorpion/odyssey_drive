package kz.divtech.odyssey.drive.data.dto.task_detail


import com.google.gson.annotations.SerializedName
import kz.divtech.odyssey.drive.common.DateTimeUtils.formatToDate
import kz.divtech.odyssey.drive.common.DateTimeUtils.formatToTime
import kz.divtech.odyssey.drive.common.DateTimeUtils.isTaskOnTheWaiting
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.Status

data class TaskDto(
    val id: Int,
    val status: String,
    @SerializedName("action_start_time")
    val actionStartTime: String,
    @SerializedName("action_end_time")
    val actionEndTime: String,
    @SerializedName("actual_start_time")
    val actualStartTime: Any,
    @SerializedName("actual_end_time")
    val actualEndTime: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("from_point")
    val fromPoint: FromPoint,
    @SerializedName("to_point")
    val toPoint: ToPoint,
    val notes: List<Any>,
    @SerializedName("passenger_application_pairs")
    val passengerApplicationPairs: List<PassengerApplicationPair>,
    @SerializedName("passenger_count")
    val passengerCount: Int,
    @SerializedName("rejected_comment")
    val rejectedComment: String
)

fun TaskDto.toTask(): Task{
    return Task(
        id = id,
        status = this.getTaskStatus(),
        passengerCount =  this.passengerCount,
        departureArrivalPlace = "${this.fromPoint.name} - ${this.toPoint.name}",
        date =  this.actionStartTime.formatToDate(),
        time = "${this.actionStartTime.formatToTime()} - ${this.actionEndTime.formatToTime()}"
    )
}

fun TaskDto.getTaskStatus(): Status {
    return when(this.status){
        "active" -> {
            when(this.isTaskOnTheWaiting()){
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
