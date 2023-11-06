package kz.divtech.odyssey.drive.domain.model.auth

data class LoginRequest(
    val phone_number: String,
    val password: String
)