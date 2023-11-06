package kz.divtech.odyssey.drive.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kz.divtech.odyssey.drive.data.dto.my_tasks.toTaskList
import kz.divtech.odyssey.drive.data.remote.ApiService
import kz.divtech.odyssey.drive.domain.model.main.Task

class ActiveTaskPagingSource (private val api: ApiService, private val date: String): PagingSource<Int, Task>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Task> {
        return try {
            val page = params.key ?: 1
            val taskListDto = api.getActiveTasks(page = page, fromDate = date, toDate = date)

            val tasks = taskListDto.toTaskList()
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (tasks.isEmpty()) null else page + 1

            LoadResult.Page(
                data = tasks,
                 prevKey = prevKey,
                 nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Task>): Int {
        return 1
    }


}