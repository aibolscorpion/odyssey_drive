package kz.divtech.odyssey.drive.domain.model.profile

data class ProfileState(val isLoading: Boolean = false,
                        val profile: Profile = emptyProfile,
                        val error: String = "")