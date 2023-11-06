package kz.divtech.odyssey.drive.domain.model.task_detail

import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.domain.model.main.emptyTask

data class TaskDetailState(
    val isLoading: Boolean = false,
    val error: String = "",
    val task: Task = emptyTask)