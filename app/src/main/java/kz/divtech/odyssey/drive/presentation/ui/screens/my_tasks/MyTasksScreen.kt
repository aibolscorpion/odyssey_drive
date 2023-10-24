package kz.divtech.odyssey.drive.presentation.ui.screens.my_tasks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.presentation.ui.MainActivityViewModel
import kz.divtech.odyssey.drive.common.Variables.PaddingDp
import kz.divtech.odyssey.drive.presentation.ui.screens.main.SingleTask
import kz.divtech.odyssey.drive.presentation.theme.OdysseyDriveTheme
import kz.divtech.odyssey.drive.presentation.ui.screens.BottomNavItem
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale

@Preview
@Composable
fun MyAssignmentsScreenPreview(){
    MyAssignmentsScreen(mainViewModel = viewModel(), onTaskClicked = {})
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyAssignmentsScreen(mainViewModel: MainActivityViewModel,
                        viewModel: MyTasksViewModel = hiltViewModel(),
                        onTaskClicked: (taskId: Int) -> Unit
) {

    val tabs = listOf(stringResource(R.string.active), stringResource(R.string.archive))
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size})

    OdysseyDriveTheme {
        val screenTitle = stringResource(BottomNavItem.MyAssignments.title)
        LaunchedEffect(Unit){
            mainViewModel.setTitle(screenTitle)
        }

        Column(modifier = Modifier
            .background(color = Color(0xFFEBEDF0))) {
            TabRow(selectedTabIndex = pagerState.currentPage,
                containerColor = Color.White){
                tabs.forEachIndexed{ index, title ->
                    Tab(text = {Text(title)},
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(index) }
                        },
                        selected = pagerState.currentPage == index,
                        unselectedContentColor = Color(0xFF243254)
                    )
                }
            }

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = PaddingDp, start = PaddingDp, end = PaddingDp, bottom = 0.dp),
                verticalAlignment = Alignment.Top,
                state = pagerState){ pageIndex ->
                when(pageIndex){
                    0 -> ActiveAssignmentsScreen(viewModel, onTaskClicked)
                    1 -> ArchiveAssignmentsScreen(viewModel, onTaskClicked)
                }
            }
        }
    }
}

@Composable
fun ActiveAssignmentsScreen(viewModel: MyTasksViewModel,
                            onTaskClicked: (taskId: Int) -> Unit){
    val activeTaskList = viewModel.activeTasksState.value.activeTasks
    LaunchedEffect(Unit){
        viewModel.getActiveTasks()
    }

    Column {
        setDatePickerDialog()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 24.dp)
        ) {
            itemsIndexed(items = activeTaskList){ _, task ->
                SingleTask(onTaskClicked, task)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun setDatePickerDialog(){
    val calendar = Calendar.getInstance()
    val currentDate = calendar.timeInMillis.toLocalDate()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis,
        selectableDates = object : SelectableDates{
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return (utcTimeMillis.toLocalDate() >= currentDate)
            }
        })

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(calendar.timeInMillis) }

    val dayOfWeekFormatter = SimpleDateFormat("dd MMM EE", Locale("ru"))

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
                    color = Color.Black,
                )
                ){
                    append(dayOfWeekFormatter.format(datePickerState.selectedDateMillis))
                }
                if(selectedDate.toLocalDate()  == currentDate){
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
                    selectedDate = datePickerState.selectedDateMillis!!
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

@Composable
fun ArchiveAssignmentsScreen(viewModel: MyTasksViewModel, onAssignmentClicked: (taskId: Int) -> Unit){
    val archiveList = viewModel.archiveTasksState.value.archiveTasks
    LaunchedEffect(Unit){
        viewModel.getArchiveTasks()
    }

    LazyColumn(contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp))  {
        itemsIndexed(items = archiveList){ _, task ->
            SingleTask(onAssignmentClicked, task = task)
        }
    }
}

fun Long.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()


