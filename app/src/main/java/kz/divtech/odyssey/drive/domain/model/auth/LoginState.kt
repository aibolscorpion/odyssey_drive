package kz.divtech.odyssey.drive.domain.model.auth

data class LoginState(
    val isLoading: Boolean = false,
    val loggedIn: Boolean = false,
    val error: String = ""
)
