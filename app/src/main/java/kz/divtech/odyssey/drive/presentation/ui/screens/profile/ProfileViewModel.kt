package kz.divtech.odyssey.drive.presentation.ui.screens.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.auth.LogoutState
import kz.divtech.odyssey.drive.domain.model.profile.ProfileState
import kz.divtech.odyssey.drive.domain.model.profile.emptyProfile
import kz.divtech.odyssey.drive.domain.use_case.profile.GetProfileUseCase
import kz.divtech.odyssey.drive.domain.use_case.auth.LogoutUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val useCase: GetProfileUseCase,
                                           private val logoutUseCase: LogoutUseCase
): ViewModel() {
    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val _logoutState = mutableStateOf(LogoutState())
    val logoutState: State<LogoutState> = _logoutState

    init {
        getProfile()
    }

    private fun getProfile(){
        viewModelScope.launch {
            val response = useCase.execute(Unit)
            response.onEach { result ->
                when(result){
                    is Resource.Success -> _profileState.value = ProfileState(profile = result.data ?: emptyProfile)
                    is Resource.Error -> _profileState.value = ProfileState(error = result.message ?: "Произошла ошибка при получений профиля")
                    is Resource.Loading -> _profileState.value = ProfileState(isLoading = true)
                }
            }.launchIn(viewModelScope)
        }
    }

    fun logout(){
        viewModelScope.launch {
            val response = logoutUseCase.execute(Unit)
            response.onEach { result ->
                when(result){
                    is Resource.Success -> _logoutState.value = LogoutState(isLoggedOut = result.data ?: false)
                    is Resource.Error -> _logoutState.value = LogoutState(error = result.message ?: "Произошла ошибка при удалений токена")
                    is Resource.Loading -> _logoutState.value = LogoutState(isLoading = true)
                }
            }.launchIn(viewModelScope)
        }
    }

}