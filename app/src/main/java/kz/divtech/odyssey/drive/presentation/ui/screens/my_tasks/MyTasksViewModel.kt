package kz.divtech.odyssey.drive.presentation.ui.screens.my_tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.domain.use_case.my_tasks.GetActiveTasksUseCase
import kz.divtech.odyssey.drive.domain.use_case.my_tasks.GetArchiveTasksUseCase
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MyTasksViewModel @Inject constructor(private val getActiveTasksUseCase: GetActiveTasksUseCase,
                                           private val getArchiveTasksUseCase: GetArchiveTasksUseCase
): ViewModel() {

    private val _activeTaskState: MutableStateFlow<PagingData<Task>> = MutableStateFlow(value = PagingData.empty())
    val activeTaskState: MutableStateFlow<PagingData<Task>>  = _activeTaskState

    private val _archiveTaskState: MutableStateFlow<PagingData<Task>> = MutableStateFlow(value = PagingData.empty())
    val archiveTaskState: MutableStateFlow<PagingData<Task>>  = _archiveTaskState

    fun getActiveTasks(date: LocalDate){
        viewModelScope.launch {
            getActiveTasksUseCase.getActiveTasks(date)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                    _activeTaskState.value = it
                }
        }
    }

    fun getArchiveTasks() {
        viewModelScope.launch {
            getArchiveTasksUseCase.getArchiveTasks()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                    _archiveTaskState.value = it
                }
        }
    }
}