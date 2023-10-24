package kz.divtech.odyssey.drive.data.dto.task_detail


import com.google.gson.annotations.SerializedName

data class PassengerApplicationPair(
    @SerializedName("application")
    val application: Application,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_passed")
    val isPassed: Boolean,
    @SerializedName("passenger")
    val passenger: Passenger,
    @SerializedName("seat_number")
    val seatNumber: Int
)