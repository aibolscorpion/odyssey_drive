package kz.divtech.odyssey.drive.data.dto.task_detail.passengers


import com.google.gson.annotations.SerializedName

data class PassengerDto(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("iin")
    val iin: String,
    @SerializedName("position")
    val position: Any
)