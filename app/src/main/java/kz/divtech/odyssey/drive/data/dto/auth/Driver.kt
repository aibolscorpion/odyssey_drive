package kz.divtech.odyssey.drive.data.dto.auth


import com.google.gson.annotations.SerializedName

data class Driver(
    val id: Int,
    val company: Company,
    val name: String,
    val surname: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
)