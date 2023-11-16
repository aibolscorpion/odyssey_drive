package kz.divtech.odyssey.drive.presentation.ui.screens.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.data.dto.main.SuccessResponse
import kz.divtech.odyssey.drive.domain.model.main.DailyInfoState
import kz.divtech.odyssey.drive.domain.model.main.ShiftState
import kz.divtech.odyssey.drive.domain.model.main.ShiftStatus
import kz.divtech.odyssey.drive.domain.model.main.ShiftTime
import kz.divtech.odyssey.drive.domain.model.main.ShiftTimeState
import kz.divtech.odyssey.drive.domain.model.main.emptyDailyInfo
import kz.divtech.odyssey.drive.domain.model.profile.ProfileState
import kz.divtech.odyssey.drive.domain.model.profile.emptyProfile
import kz.divtech.odyssey.drive.domain.use_case.main.ActivateShiftUseCase
import kz.divtech.odyssey.drive.domain.use_case.main.DeactivateShiftUseCase
import kz.divtech.odyssey.drive.domain.use_case.main.GetDailyInfoUseCase
import kz.divtech.odyssey.drive.domain.use_case.profile.GetProfileUseCase
import kz.divtech.odyssey.drive.domain.use_case.main.GetShiftStartedTimeUseCase
import kz.divtech.odyssey.drive.domain.use_case.main.PauseShiftUseCase
import kz.divtech.odyssey.drive.domain.use_case.main.ResumeShiftUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dailyInfoUseCase: GetDailyInfoUseCase,
                                        private val getProfileUseCase: GetProfileUseCase,
                                        private val activateUseCase: ActivateShiftUseCase,
                                        private val pauseUseCase: PauseShiftUseCase,
                                        private val resumeUseCase: ResumeShiftUseCase,
                                        private val deactivateUseCase: DeactivateShiftUseCase,
                                        private val getShiftTime: GetShiftStartedTimeUseCase
): ViewModel() {

    private val _dailyInfoState = mutableStateOf(value = DailyInfoState())
    val dailyInfoState : State<DailyInfoState> = _dailyInfoState

    private val _profileState = mutableStateOf(value = ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val _shiftState = mutableStateOf(value = ShiftState())
    val shiftState: State<ShiftState> = _shiftState

    private val _shiftTimeState = mutableStateOf(value = ShiftTimeState())
    val shiftTimeState: State<ShiftTimeState> = _shiftTimeState

    init {
        getProfile()
    }

    fun getDailyInfo(){

        viewModelScope.launch {
                dailyInfoUseCase.execute(Unit).onEach { result ->
                    when(result){
                        is Resource.Success -> {
                            _dailyInfoState.value = DailyInfoState(dailyInfo = result.data ?: emptyDailyInfo)
                        }

                        is Resource.Loading -> _dailyInfoState.value = _dailyInfoState.value.copy(isLoading = true)
                        is Resource.Error -> _dailyInfoState.value = DailyInfoState(error = result.message ?: "")
                    }
                }.launchIn(viewModelScope)
            }
        }

    private fun getProfile(){
        viewModelScope.launch {
            val response = getProfileUseCase.execute(Unit)
            response.onEach { result ->
                when(result){
                    is Resource.Success -> _profileState.value = ProfileState(profile = result.data ?: emptyProfile)
                    is Resource.Error -> _profileState.value = ProfileState(error = result.message ?: "Произошла ошибка при получений профиля")
                    is Resource.Loading -> _profileState.value = ProfileState(isLoading = true)
                }
            }.launchIn(viewModelScope)
        }
    }


    fun activateShift(){
        viewModelScope.launch {
            val response = activateUseCase.execute(Unit)
            defineResource(response, ShiftStatus.ONLINE)
        }
    }

    fun pauseShift(){
        viewModelScope.launch {
            val response = pauseUseCase.execute(Unit)
            defineResource(response, ShiftStatus.ON_BREAK)
        }
    }

    fun resumeShift(){
        viewModelScope.launch {
            val response = resumeUseCase.execute(Unit)
            defineResource(response, ShiftStatus.ONLINE)
        }
    }

    fun deactivateShift(){
        viewModelScope.launch {
            val response = deactivateUseCase.execute(Unit)
            defineResource(response, ShiftStatus.OFFLINE)
        }
    }

    private fun defineResource(response: Flow<Resource<SuccessResponse>>, shiftState: ShiftStatus) {
        response.onEach { result ->
            when(result){
                is Resource.Success -> _shiftState.value = ShiftState(status = shiftState)
                is Resource.Error -> _shiftState.value = ShiftState(error = result.message ?: "Произошла ошибка при получений профиля")
                is Resource.Loading -> _shiftState.value = ShiftState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

    fun getShiftTime(){
        viewModelScope.launch {
            val response = getShiftTime.execute(Unit)
            response.onEach { result ->
                when(result){
                    is Resource.Success -> {
                        _shiftTimeState.value = ShiftTimeState(shiftTime = result.data ?: ShiftTime(ShiftStatus.OFFLINE, null))
                        _shiftState.value = ShiftState(status = result.data?.shiftStatus ?: ShiftStatus.OFFLINE)
                    }
                    is Resource.Error -> _shiftTimeState.value = ShiftTimeState(error = result.message ?: "Произошла ошибка при получений профиля")
                    is Resource.Loading -> _shiftTimeState.value = ShiftTimeState(isLoading = true)
                }
            }.launchIn(viewModelScope)
        }
    }
}