package kz.divtech.odyssey.drive.data.dto.profile


import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    val name: String
)