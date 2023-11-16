package kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.passengers

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.task_detail.PassengersState
import kz.divtech.odyssey.drive.domain.use_case.task_detail.GetPassengersUseCase
import javax.inject.Inject

@HiltViewModel
class PassengersViewModel @Inject constructor(private val getPassengersUseCase: GetPassengersUseCase) : ViewModel() {

    private val _passengersState = mutableStateOf(PassengersState())
    val passengersState: State<PassengersState> = _passengersState

    fun getPassengerList(taskId: Int){
        viewModelScope.launch {
            getPassengersUseCase.execute(taskId).onEach { result ->
                when(result){
                    is Resource.Success -> _passengersState.value = PassengersState(passengerList = result.data ?: emptyList())
                    is Resource.Error -> _passengersState.value = PassengersState(error = result.message ?: "Произошла ошибка при получений списка пассажиров")
                    is Resource.Loading -> _passengersState.value = PassengersState(isLoading = true)
                }
            }.launchIn(viewModelScope)
        }
    }


}