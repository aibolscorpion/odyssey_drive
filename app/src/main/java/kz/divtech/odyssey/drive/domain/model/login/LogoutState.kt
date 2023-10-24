package kz.divtech.odyssey.drive.domain.model.login

class LogoutState(
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false,
    val error: String = "") {
}