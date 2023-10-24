package kz.divtech.odyssey.drive.domain.model.login

data class LoginRequest(
    val phone_number: String,
    val password: String
)