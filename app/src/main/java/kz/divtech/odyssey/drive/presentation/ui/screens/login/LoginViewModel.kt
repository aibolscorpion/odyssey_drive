package kz.divtech.odyssey.drive.presentation.ui.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.auth.LoginRequest
import kz.divtech.odyssey.drive.domain.model.auth.LoginState
import kz.divtech.odyssey.drive.domain.use_case.auth.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel(){
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun login(phoneNumber: String, password: String){
        viewModelScope.launch {
                loginUseCase.login(LoginRequest(phoneNumber, password)).onEach { result ->
                when(result){
                    is Resource.Success -> {
                        _state.value = LoginState(loggedIn = result.data?.accessToken?.isNotBlank() ?: false)
                    }
                    is Resource.Error -> {
                        _state.value = LoginState(error = result.message ?: "Произошла неожиданная ошибка")
                    }
                    is Resource.Loading -> {
                        _state.value = LoginState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}