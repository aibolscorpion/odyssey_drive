package kz.divtech.odyssey.drive.data.dto.task_detail


import com.google.gson.annotations.SerializedName

data class TaskActionResponse(
    val success: Boolean,
    @SerializedName("task_id")
    val taskId: Int
)