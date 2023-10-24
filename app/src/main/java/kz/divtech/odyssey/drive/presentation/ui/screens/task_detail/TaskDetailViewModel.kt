package kz.divtech.odyssey.drive.presentation.ui.screens.task_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.main.emptyTask
import kz.divtech.odyssey.drive.domain.model.task_detail.TaskDetailState
import kz.divtech.odyssey.drive.domain.use_case.main.GetTaskByIdUseCase
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(private val useCase: GetTaskByIdUseCase): ViewModel() {
    private val _taskDetailState = mutableStateOf(TaskDetailState())
    val taskDetailState: State<TaskDetailState> = _taskDetailState

     fun getTaskById(id: Int){
         viewModelScope.launch {
             useCase.getTaskById(id).onEach { result ->
                 when(result){
                     is Resource.Success -> _taskDetailState.value = TaskDetailState(task = result.data ?: emptyTask)
                     is Resource.Error -> _taskDetailState.value = TaskDetailState(error = result.message ?: "Произошла ошибка при получений задания")
                     is Resource.Loading -> _taskDetailState.value = TaskDetailState(isLoading = true)
                 }
             }.launchIn(viewModelScope)
         }
     }


}