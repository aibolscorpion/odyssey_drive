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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.common.DateTimeUtils.formatToDatePickerString
import kz.divtech.odyssey.drive.common.DateTimeUtils.toLocalDate
import kz.divtech.odyssey.drive.common.Variables
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.presentation.theme.ColorPrimaryText
import kz.divtech.odyssey.drive.presentation.theme.ColorTypoSecondary
import kz.divtech.odyssey.drive.presentation.ui.screens.main.CenterCircularProgressIndicator
import kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.StatusText
import java.time.LocalDate
import java.util.Calendar


@Composable
fun ActiveTasksScreen(viewModel: MyTasksViewModel,
                      onTaskClicked: (taskId: Int) -> Unit,
                      snackBarHostState: SnackbarHostState){

    val scope = rememberCoroutineScope()
    val taskPagingItems: LazyPagingItems<Task> = viewModel.activeTaskState.collectAsLazyPagingItems()


    Column {
        setDatePickerDialog { selectedDate ->
            viewModel.getActiveTasks(selectedDate)
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 24.dp)
        ) {
            items(taskPagingItems.itemCount){ index ->
                ActiveTaskItem(onTaskClicked, taskPagingItems[index]!!)
            }
            taskPagingItems.apply {
                when{
                    loadState.refresh is LoadState.Loading -> {
                        item { CenterCircularProgressIndicator() }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = taskPagingItems.loadState.refresh as LoadState.Error
                        item {
                            scope.launch {
                                snackBarHostState.showSnackbar(error.error.localizedMessage!!)
                            }
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            CenterCircularProgressIndicator()
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = taskPagingItems.loadState.append as LoadState.Error
                        item {
                            scope.launch {
                                snackBarHostState.showSnackbar(error.error.localizedMessage!!)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun setDatePickerDialog(onDateSelectedListener: (selectedDate: LocalDate) -> Unit){
    val currentDateMillis =  Calendar.getInstance().timeInMillis
    var selectedDate by remember { mutableStateOf(currentDateMillis.toLocalDate()) }
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        onDateSelectedListener(selectedDate)
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentDateMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return (utcTimeMillis.toLocalDate() >= currentDateMillis.toLocalDate())
            }
        })

    Card(onClick = {
        showDatePicker = true
    }) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Color.Black)
                ){
                    append(selectedDate.formatToDatePickerString())
                }
                if(selectedDate  == currentDateMillis.toLocalDate()){
                    withStyle(style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0x80121F33),
                    )
                    ){
                        append(", ${stringResource(R.string.today)}")
                    }
                }
            })

            Icon(painter = painterResource(R.drawable.icon_calendar),
                contentDescription = null)
        }
    }

    if(showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    datePickerState.selectedDateMillis?.let { selectedDate = it.toLocalDate() }
                    onDateSelectedListener(selectedDate)
                }) {
                    Text(text = stringResource(R.string.confirm))
                }
            }, dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text(text = stringResource(R.string.cancel))
                }
            }) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveTaskItem(onTaskClick: (taskId: Int) -> Unit, task: Task){

    Card(modifier = Modifier
        .fillMaxWidth(),
        onClick = {
            onTaskClick(task.id)
        }
    ) {
        Box(modifier = Modifier
            .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = Variables.PaddingDp)){
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = task.time,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(600),
                            color = ColorPrimaryText,
                        )
                    )

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

