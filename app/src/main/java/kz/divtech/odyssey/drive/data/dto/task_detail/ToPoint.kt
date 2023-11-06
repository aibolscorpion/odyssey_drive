package kz.divtech.odyssey.drive.data.dto.task_detail


import com.google.gson.annotations.SerializedName

data class ToPoint(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("is_active")
    val isActive: Boolean,
    val latitude: String,
    val longitude: String,
    val name: String
)