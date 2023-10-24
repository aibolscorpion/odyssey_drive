package kz.divtech.odyssey.drive.data.dto.main


import com.google.gson.annotations.SerializedName

data class Driver(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("daily_status")
    val dailyStatus: String,
    val id: Int,
    @SerializedName("middle_name")
    val middleName: String,
    val name: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val status: String,
    val surname: String
)