package kz.divtech.odyssey.drive.domain.model.auth

class LogoutState(
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false,
    val error: String = "") {
}