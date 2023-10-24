package kz.divtech.odyssey.drive.domain.model.my_tasks

import kz.divtech.odyssey.drive.domain.model.main.Task

class ArchiveTasksState(val isLoading: Boolean = false,
                        val error: String = "",
                        val archiveTasks: List<Task> = emptyList())