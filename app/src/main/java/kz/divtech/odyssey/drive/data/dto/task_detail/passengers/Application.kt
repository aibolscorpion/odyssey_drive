package kz.divtech.odyssey.drive.data.dto.task_detail.passengers


import com.google.gson.annotations.SerializedName

data class Application(
    @SerializedName("action_date")
    val actionDate: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("external_user_created")
    val externalUserCreated: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("passenger_count")
    val passengerCount: Int,
    @SerializedName("planning_end_time")
    val planningEndTime: String,
    @SerializedName("planning_start_time")
    val planningStartTime: String,
    @SerializedName("source_type")
    val sourceType: String,
    @SerializedName("status")
    val status: String
)