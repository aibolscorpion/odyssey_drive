package kz.divtech.odyssey.drive.data.dto.profile


import com.google.gson.annotations.SerializedName

data class CurrentVehicle(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("is_available")
    val isAvailable: Boolean,
    val mark: Mark,
    val model: Model,
    val name: String,
    @SerializedName("seat_count")
    val seatCount: Int,
    @SerializedName("state_number")
    val stateNumber: String,
    val vin: String,
    val year: Int
)