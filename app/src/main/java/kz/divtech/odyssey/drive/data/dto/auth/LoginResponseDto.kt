package kz.divtech.odyssey.drive.data.dto.auth


import com.google.gson.annotations.SerializedName
import kz.divtech.odyssey.drive.domain.model.auth.LoginResponse

data class LoginResponseDto(
    @SerializedName("access_token")
    val accessToken: String,
    val driver: Driver
)

fun LoginResponseDto.toLoginResponse(): LoginResponse {
    return LoginResponse(accessToken = accessToken)
}