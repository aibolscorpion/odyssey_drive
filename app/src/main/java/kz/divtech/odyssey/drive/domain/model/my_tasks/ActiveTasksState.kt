package kz.divtech.odyssey.drive.domain.model.my_tasks

import kz.divtech.odyssey.drive.domain.model.main.Task

class ActiveTasksState(val isLoading: Boolean = false,
                       val error: String = "",
                       val activeTasks: List<Task> = emptyList())