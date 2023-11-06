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
import kz.divtech.odyssey.drive.domain.model.task_detail.TaskStatusState
import kz.divtech.odyssey.drive.domain.use_case.main.GetTaskByIdUseCase
import kz.divtech.odyssey.drive.domain.use_case.task_detail.BeginTaskUseCase
import kz.divtech.odyssey.drive.domain.use_case.task_detail.CompleteTaskUseCase
import kz.divtech.odyssey.drive.domain.use_case.task_detail.RejectTaskUseCase
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskByIdUseCase,
    private val beginTaskUseCase: BeginTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val rejectTaskUseCase: RejectTaskUseCase): ViewModel() {

    private val _taskDetailState = mutableStateOf(TaskDetailState())
    val taskDetailState: State<TaskDetailState> = _taskDetailState

    private val _taskStatusState = mutableStateOf(TaskStatusState())
    val taskStatusState: State<TaskStatusState> = _taskStatusState


     fun getTaskById(id: Int){
         viewModelScope.launch {
             getTaskUseCase.getTaskById(id).onEach { result ->
                 when(result){
                     is Resource.Success -> {
                         _taskDetailState.value = TaskDetailState(task = result.data ?: emptyTask)
                     }
                     is Resource.Error -> _taskDetailState.value = TaskDetailState(error = result.message ?: "Произошла ошибка при получений задания")
                     is Resource.Loading -> _taskDetailState.value = TaskDetailState(isLoading = true)
                 }
             }.launchIn(viewModelScope)
         }
     }

    fun beginTask(taskId: Int){
        viewModelScope.launch {
            beginTaskUseCase.beginTask(taskId).onEach { result ->
                when(result){
                    is Resource.Success -> {
                        getTaskById(taskId)
                        _taskStatusState.value = TaskStatusState(isLoading = false)
                    }
                    is Resource.Error -> _taskStatusState.value = TaskStatusState(error = result.message ?: "")
                    is Resource.Loading -> _taskStatusState.value = TaskStatusState(isLoading = true)
                }
            }.launchIn(viewModelScope)
        }
    }
    fun completeTask(taskId: Int){
        viewModelScope.launch {
            completeTaskUseCase.completeTask(taskId).onEach{ result ->
                when(result){
                    is Resource.Success -> {
                        getTaskById(taskId)
                        _taskStatusState.value = TaskStatusState(isLoading = false)
                    }
                    is Resource.Error -> _taskStatusState.value = TaskStatusState(error = result.message ?: "")
                    is Resource.Loading -> _taskStatusState.value = TaskStatusState(isLoading = true)
                }
            }.launchIn(viewModelScope)
        }
    }
    fun rejectTask(taskId: Int, comment: String){
        viewModelScope.launch {
            rejectTaskUseCase.rejectTask(taskId, comment).onEach{ result ->
                when(result){
                    is Resource.Success -> {
                        getTaskById(taskId)
                        _taskStatusState.value = TaskStatusState(isLoading = false)
                    }
                    is Resource.Error -> _taskStatusState.value = TaskStatusState(error = result.message ?: "")
                    is Resource.Loading -> _taskStatusState.value = TaskStatusState(isLoading = true)
                }
            }.launchIn(viewModelScope)
        }
    }


}