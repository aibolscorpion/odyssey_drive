package kz.divtech.odyssey.drive.data.dto.my_tasks

import kz.divtech.odyssey.drive.data.dto.task_detail.TaskDto
import kz.divtech.odyssey.drive.data.dto.task_detail.toTask
import kz.divtech.odyssey.drive.domain.model.main.Task


data class TaskListDto(
    val `data`: List<TaskDto>,
    val limit: Int,
    val page: Int,
    val total: Int
)
fun TaskListDto.toTaskList(): List<Task> =
    this.data.map { taskDto ->  taskDto.toTask() }