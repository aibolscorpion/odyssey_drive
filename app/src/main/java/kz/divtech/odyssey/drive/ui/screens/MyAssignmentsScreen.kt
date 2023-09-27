package kz.divtech.odyssey.drive.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.ui.BottomNavItem
import kz.divtech.odyssey.drive.ui.MainViewModel
import kz.divtech.odyssey.drive.ui.Variables.PaddingDp
import kz.divtech.odyssey.drive.ui.theme.OdysseyDriveTheme
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale

@Preview
@Composable
fun MyAssignmentsScreenPreview(){
    MyAssignmentsScreen(mainViewModel = viewModel(), onAssignmentClicked = {})
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyAssignmentsScreen(mainViewModel: MainViewModel, onAssignmentClicked: (id: Int) -> Unit) {
    val tabs = listOf(stringResource(R.string.active), stringResource(R.string.archive))
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size})

    OdysseyDriveTheme {
        val screenTitle = stringResource(BottomNavItem.MyAssignments.title)
        LaunchedEffect(Unit){
            mainViewModel.setTitle(screenTitle)
        }

        Column {
            TabRow(selectedTabIndex = pagerState.currentPage){
                tabs.forEachIndexed{ index, title ->
                    Tab(text = { Text(title)},
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(index) }
                        },
                        selected = pagerState.currentPage == index
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
                    0 -> ActiveAssignmentsScreen(onAssignmentClicked)
                    1 -> ArchiveAssignmentsScreen(onAssignmentClicked)
                }
            }
        }
    }
}

@Composable
fun ActiveAssignmentsScreen(onAssignmentClicked: (id: Int) -> Unit){
    val nearestAssignmentTime = "08:00 – 20:00"
    val stationNames = "Вокзал «Актогай» — ГОК"
    val assigmentId = 163
    val peopleNumber = 14
    val assigmentStatus = "В работе"
    Column {
        setDatePickerDialog()

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 24.dp)
        ) {
            itemsIndexed(items = listOf(1, 2, 3, 4, 5)){ index, name ->
                SingleAssignment(
                    onAssignmentClicked,
                    nearestAssignmentTime,
                    stationNames,
                    assigmentId,
                    peopleNumber,
                    assigmentStatus
                )
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
fun ArchiveAssignmentsScreen(onAssignmentClicked: (id: Int) -> Unit){
    val nearestAssignmentTime = "08:00 – 20:00"
    val stationNames = "Вокзал «Актогай» — ГОК"
    val assigmentId = 163
    val peopleNumber = 14
    val assigmentStatus = "В работе"
    LazyColumn(contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp))  {
        items(count = 10){
            SingleAssignment(
                onAssignmentClicked,
                nearestAssignmentTime,
                stationNames,
                assigmentId,
                peopleNumber,
                assigmentStatus
            )
        }
    }
}

fun Long.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()


