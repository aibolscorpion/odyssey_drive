package kz.divtech.odyssey.drive.presentation.ui.screens.my_tasks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.my_tasks.ActiveTasksState
import kz.divtech.odyssey.drive.domain.model.my_tasks.ArchiveTasksState
import kz.divtech.odyssey.drive.domain.use_case.my_tasks.GetActiveTasksUseCase
import kz.divtech.odyssey.drive.domain.use_case.my_tasks.GetArchiveTasksUseCase
import javax.inject.Inject

@HiltViewModel
class MyTasksViewModel @Inject constructor(private val getActiveTasksUseCase: GetActiveTasksUseCase,
                                           private val getArchiveTasksUseCase: GetArchiveTasksUseCase
): ViewModel() {
    private val _activeTasksState = mutableStateOf(ActiveTasksState())
    val activeTasksState: State<ActiveTasksState> = _activeTasksState

    private val _archiveTasksState = mutableStateOf(ArchiveTasksState())
    val archiveTasksState: State<ArchiveTasksState> = _archiveTasksState

    fun getActiveTasks(){
        viewModelScope.launch {
            getActiveTasksUseCase.getActiveTasks().onEach { result ->
                when(result){
                    is Resource.Success -> _activeTasksState.value = ActiveTasksState(activeTasks = result.data ?: emptyList())
                    is Resource.Loading -> _activeTasksState.value = ActiveTasksState(isLoading = true)
                    is Resource.Error -> _activeTasksState.value = ActiveTasksState(error = result.message ?:
                        "Неизвестная ошибка при получение списка активных заявок")
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getArchiveTasks(){
        viewModelScope.launch {
            getArchiveTasksUseCase.getArchiveTasks().onEach { result ->
                when(result){
                    is Resource.Success -> _archiveTasksState.value = ArchiveTasksState(archiveTasks = result.data ?: emptyList())
                    is Resource.Loading -> _archiveTasksState.value = ArchiveTasksState(isLoading = true)
                    is Resource.Error -> _archiveTasksState.value = ArchiveTasksState(error = result.message?:
                        "Неизвестная ошибка при получение списка архивных заявок")
                }
            }.launchIn(viewModelScope)
        }
    }
}