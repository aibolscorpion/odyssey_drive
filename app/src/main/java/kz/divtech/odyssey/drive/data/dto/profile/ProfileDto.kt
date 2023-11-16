package kz.divtech.odyssey.drive.data.dto.profile


import com.google.gson.annotations.SerializedName
import kz.divtech.odyssey.drive.domain.model.profile.Profile

data class ProfileDto(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("current_vehicle")
    val currentVehicle: CurrentVehicle?,
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

fun ProfileDto.toProfile() =
    Profile(name = name,
        surname = surname,
        patronymic = middleName,
        vehicleName = if(currentVehicle != null)
            "${currentVehicle.mark.name} ${currentVehicle.model.name} ${currentVehicle.year}"
        else "",
        stateNumber = currentVehicle?.stateNumber ?: "",
        seatCount = currentVehicle?.seatCount ?: 0)