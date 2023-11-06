package kz.divtech.odyssey.drive.data.dto.auth


import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    val name: String
)