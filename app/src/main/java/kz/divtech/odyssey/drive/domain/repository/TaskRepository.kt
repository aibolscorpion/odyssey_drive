package kz.divtech.odyssey.drive.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.divtech.odyssey.drive.common.Resource
import kz.divtech.odyssey.drive.domain.model.main.Task

interface TaskRepository {
    suspend fun getTaskById(id: Int): Flow<Resource<Task>>
    suspend fun getActiveTasks(): Flow<Resource<List<Task>>>
    suspend fun getArchiveTasks(): Flow<Resource<List<Task>>>
}