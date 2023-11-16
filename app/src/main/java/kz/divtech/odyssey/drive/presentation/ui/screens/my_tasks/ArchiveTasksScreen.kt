package kz.divtech.odyssey.drive.presentation.ui.screens.my_tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.common.Variables
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.presentation.theme.ColorPrimaryText
import kz.divtech.odyssey.drive.presentation.theme.ColorTypoSecondary
import kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.StatusText


@Composable
fun ArchiveTasksScreen(viewModel: MyTasksViewModel, onAssignmentClicked: (taskId: Int) -> Unit,
                       snackBarHostState: SnackbarHostState){
    val taskPagingItems: LazyPagingItems<Task> = viewModel.archiveTaskState.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        viewModel.getArchiveTasks()
    }

    Column {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp))  {

            items(taskPagingItems.itemCount){ index ->
                ArchiveTaskItem(onAssignmentClicked, task = taskPagingItems[index]!!)
            }

//            when(taskPagingItems.loadState.refresh) {
//                is LoadState.Loading -> {
//                    item { CenterCircularProgressIndicator() }
//                }
//
//                is LoadState.Error -> {
//                    val error = taskPagingItems.loadState.refresh as LoadState.Error
//                    item {
//                        scope.launch {
//                            snackBarHostState.showSnackbar(error.error.localizedMessage!!)
//                        }
//                    }
//                }
//
//                else -> {}
//            }
//
//            when(taskPagingItems.loadState.append){
//                is LoadState.Loading -> {
//                    item {
//                        CenterCircularProgressIndicator()
//                    }
//                }
//
//                is LoadState.Error -> {
//                    val error = taskPagingItems.loadState.append as LoadState.Error
//                    item {
//                        scope.launch {
//                            snackBarHostState.showSnackbar(error.error.localizedMessage!!)
//                        }
//                    }
//                }
//                else -> {}
//            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveTaskItem(onAssignmentClick: (taskId: Int) -> Unit, task: Task){

    Card(modifier = Modifier
        .fillMaxWidth(),
        onClick = {
            onAssignmentClick(task.id)
        }
    ) {
        Box(modifier = Modifier
            .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = Variables.PaddingDp)){
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {

                    Column {
                        Text(text = task.time,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(600),
                                color = ColorPrimaryText,
                            )
                        )

                        Text(
                            text = task.date,
                            style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 16.sp,
                                fontWeight = FontWeight(400),
                                color = ColorPrimaryText,
                            )
                        )
                    }

                    StatusText(task.status)
                }

                Text(text = task.departureArrivalPlace,
                    modifier = Modifier
                        .padding(top = 8.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        color = ColorPrimaryText,
                    )
                )

                Text(modifier = Modifier
                    .padding(top = 6.dp),
                    text = "№${task.id} * ${task.passengerCount} ${stringResource(R.string.human)}",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(450),
                        color = ColorTypoSecondary,
                    )
                )
            }
        }
    }
}
