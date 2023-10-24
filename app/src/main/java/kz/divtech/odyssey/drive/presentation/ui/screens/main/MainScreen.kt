package kz.divtech.odyssey.drive.presentation.ui.screens.main

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.common.DateTimeUtils.getTodayDate
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.presentation.ui.MainActivityViewModel
import kz.divtech.odyssey.drive.common.Variables
import kz.divtech.odyssey.drive.common.Variables.PaddingDp
import kz.divtech.odyssey.drive.domain.model.main.DailyInfo
import kz.divtech.odyssey.drive.domain.model.main.ShiftStatus
import kz.divtech.odyssey.drive.domain.model.profile.Profile
import kz.divtech.odyssey.drive.domain.model.profile.getFullNameWithInitials
import kz.divtech.odyssey.drive.presentation.ui.screens.BottomNavItem
import kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.StatusText
import kz.divtech.odyssey.drive.presentation.theme.ColorPrimaryText
import kz.divtech.odyssey.drive.presentation.theme.ColorTypoSecondary
import kz.divtech.odyssey.drive.presentation.theme.GreyAlpha30
import kz.divtech.odyssey.drive.presentation.theme.GreyAlpha32
import kz.divtech.odyssey.drive.presentation.theme.GreyAlpha50
import kz.divtech.odyssey.drive.presentation.theme.OdysseyDriveTheme
import kz.divtech.odyssey.drive.presentation.ui.components.OutlinedButton
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.time.Duration.Companion.seconds

@Preview
@Composable
fun HomeScreenPreview(){
    MainScreen(mainActivityViewModel = viewModel(), onTaskClick = {})
}

@Composable
fun MainScreen(mainActivityViewModel: MainActivityViewModel,
               mainViewModel: MainViewModel = hiltViewModel(),
               onTaskClick: (taskId: Int) -> Unit
) {


    val dailyInfoState by mainViewModel.dailyInfoState
    val profileState by mainViewModel.profileState
    val shiftState by mainViewModel.shiftState
    val shiftTimeState by mainViewModel.shiftTimeState

    val dailyInfo = dailyInfoState.dailyInfo
    val profile = profileState.profile
    val shiftStatus = shiftState.status
    val shiftStartedTime = shiftTimeState.shiftTime.shiftRoutineStartTime


    if(dailyInfoState.error.isNotBlank()){
        Toast.makeText(LocalContext.current, dailyInfoState.error, Toast.LENGTH_SHORT).show()
    }

    if(profileState.error.isNotBlank()){
        Toast.makeText(LocalContext.current, profileState.error, Toast.LENGTH_SHORT).show()
    }

    if(shiftState.error.isNotBlank()){
        Toast.makeText(LocalContext.current, shiftState.error, Toast.LENGTH_SHORT).show()
    }

    if(shiftTimeState.error.isNotBlank()){
        Toast.makeText(LocalContext.current, shiftState.error, Toast.LENGTH_SHORT).show()
    }

    OdysseyDriveTheme {
        val screenTitle = stringResource(BottomNavItem.Main.title)
        LaunchedEffect(Unit){
            mainViewModel.getDailyInfo()
            mainViewModel.getShiftTime()
            mainActivityViewModel.setTitle(screenTitle)
        }

        Column(modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFEBEDF0))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)){

            DriverInfo(profile)

            AssignmentForTodayCard(dailyInfo)

            dailyInfo.nearestTask?.let{
                NearestTask(dailyInfo.nearestTask, onTaskClick)
            }

            Spacer(modifier = Modifier
                .weight(1f))

            Buttons(mainViewModel, shiftStatus, shiftStartedTime)
        }

        if(dailyInfoState.isLoading || shiftState.isLoading || profileState.isLoading ||
                shiftTimeState.isLoading){
            CenterCircularProgressIndicator()
        }
    }
}

@Composable
fun DriverInfo(profile: Profile) {

        Column {
            Text(
                text = profile.getFullNameWithInitials(),
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(600),
                    color = ColorPrimaryText,
                    letterSpacing = 0.2.sp,
                )
            )

            Text(
                text = "${profile.vehicleName} * ${profile.stateNumber}",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(500),
                    color = GreyAlpha30,
                ),
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }
}


@Composable
fun AssignmentForTodayCard(dailyInfo: DailyInfo){

    Card(modifier = Modifier
        .shadow(elevation = 2.dp, spotColor = Color(0x05001940), ambientColor = Color(0x05001940))
        .fillMaxWidth()
        .padding(top = 20.dp)
        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
    ) {
        Box(modifier = Modifier
            .padding(top = 14.dp, start = PaddingDp, end = PaddingDp, bottom = PaddingDp)){
            Column {
                Text(text = stringResource(R.string.assigment_for_today),
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        color = ColorPrimaryText,
                    )
                )

                Text(modifier = Modifier
                    .padding(top = 4.dp),
                    text = getTodayDate(),
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(500),
                        color = GreyAlpha50,
                    ))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = PaddingDp),
                    horizontalArrangement = Arrangement.SpaceAround) {

                    AssignmentCountTV(R.string.all, dailyInfo.activeTaskCount, ColorPrimaryText)

                    AssignmentCountTV(R.string.completed, dailyInfo.completedTaskCount, Color(0xFF70CD8A))

                    AssignmentCountTV(R.string.cancelled_lowercase, dailyInfo.cancelledTaskCount, color = GreyAlpha32)
                }
            }
        }
    }
}

@Composable
fun AssignmentCountTV(@StringRes text: Int, count: Int, color: Color){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = count.toString(),
            style = TextStyle(
                fontSize = 36.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight(600),
                color = color,
                textAlign = TextAlign.Center,
                letterSpacing = 0.2.sp,
            )
        )

        Text(text = stringResource(text),
            modifier = Modifier
                .padding(top = 8.dp),
            style = TextStyle(
                fontSize = 13.sp,
                lineHeight = 16.sp,
                color = GreyAlpha50,
                fontWeight = FontWeight(500)
            ))

    }
}

@Composable
fun NearestTask(task: Task, onAssignmentClick: (taskId: Int) -> Unit){
    Text(text = stringResource(R.string.nearest_assignment),
        modifier = Modifier
            .padding(top = PaddingDp, bottom = PaddingDp),
        style = TextStyle(
            fontSize = 18.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight(600),
            color = ColorPrimaryText,
        ))

    SingleTask(onAssignmentClick = onAssignmentClick, task = task)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleTask(onAssignmentClick: (taskId: Int) -> Unit, task: Task){

    Card(modifier = Modifier
        .fillMaxWidth(),
        onClick = {
            onAssignmentClick(task.id)
        }
    ) {
        Box(modifier = Modifier
            .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = PaddingDp)){
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
                        ))

                    StatusText(task.status)
                }

                Text(text = task.departureArrivalPlace,
                    modifier = Modifier
                        .padding(top = 8.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        color = ColorPrimaryText,
                    ))

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

@Composable
fun Buttons(mainViewModel: MainViewModel, shiftStatus: ShiftStatus, shiftTime: LocalDateTime?) {
    when(shiftStatus){
        ShiftStatus.OFFLINE -> {
            Button(
                onClick = {
                    mainViewModel.activateShift()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Variables.ButtonHeight),
            ) {
                Image(
                    painterResource(id = R.drawable.icon_play_circle),
                    contentDescription = stringResource(R.string.to_the_line)
                )

                Text(
                    text = stringResource(R.string.to_the_line),
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }

        }

        ShiftStatus.ONLINE -> {
            LaunchedEffect(Unit){
                mainViewModel.getShiftTime()
            }

            Row(modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){

                Text(text = stringResource(R.string.shift_lasts),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0x4D000000),
                    ))

                Timer(shiftTime)
            }

            Button(
                onClick = {
                    mainViewModel.pauseShift()
                },
                modifier = Modifier
                    .padding(top = 9.dp)
                    .fillMaxWidth()
                    .height(Variables.ButtonHeight),
            ) {
                Image(
                    painterResource(id = R.drawable.icon_pause),
                    contentDescription = stringResource(R.string.pause)
                )

                Text(
                    text = stringResource(R.string.pause),
                    modifier = Modifier
                        .padding(start = 8.dp),
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        color = Color.White,
                    )
                )
            }

            OutlinedButton(
                text = stringResource(R.string.finish_shift),
                onClick = {
                    mainViewModel.deactivateShift()
                },
                modifier = Modifier
                    .padding(top = 8.dp))
        }

        ShiftStatus.ON_BREAK -> {

            Column(modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(R.string.shift_on_pause),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0x4D000000),
                    ))
            }

            Button(
                onClick = {
                    mainViewModel.resumeShift()
                },
                modifier = Modifier
                    .padding(top = 9.dp)
                    .fillMaxWidth()
                    .height(Variables.ButtonHeight),
            ) {
                Image(
                    painterResource(id = R.drawable.icon_pause),
                    contentDescription = stringResource(R.string.pause)
                )

                Text(
                    text = stringResource(R.string.resume),
                    modifier = Modifier
                        .padding(start = 8.dp),
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        color = Color.White,
                    )
                )
            }

            OutlinedButton(
                text = stringResource(R.string.finish_shift),
                onClick = {
                    mainViewModel.deactivateShift()
                },
                modifier = Modifier
                    .padding(top = 8.dp))

        }
    }
}

@Composable
fun Timer(shiftStartedTime: LocalDateTime? = null){
    var timerSecond by remember { mutableStateOf(0) }
    shiftStartedTime?.let {
        val tempDateTime = LocalDateTime.from(shiftStartedTime)
        timerSecond = tempDateTime.until(LocalDateTime.now(), ChronoUnit.SECONDS).toInt()
    }

    LaunchedEffect(Unit){
        while(true){
          delay(1.seconds)
          timerSecond++
        }
    }

    val minute = 60
    val hour = 60 * minute
    val hours = timerSecond / hour
    val minutes = timerSecond % hour / minute
    val seconds = timerSecond % hour % minute

    Text(text = "$hours ч. $minutes м. $seconds с.",
        style = TextStyle(
            fontSize = 14.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight(600),
            color = ColorPrimaryText,
        ))
}

@Composable
fun CenterCircularProgressIndicator(){
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}
