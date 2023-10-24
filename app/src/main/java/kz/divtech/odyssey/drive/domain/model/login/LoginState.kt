package kz.divtech.odyssey.drive.domain.model.login

data class LoginState(
    val isLoading: Boolean = false,
    val loggedIn: Boolean = false,
    val error: String = ""
)
