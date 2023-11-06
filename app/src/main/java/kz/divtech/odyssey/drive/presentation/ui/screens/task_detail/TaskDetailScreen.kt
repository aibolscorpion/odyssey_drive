package kz.divtech.odyssey.drive.presentation.ui.screens.task_detail

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.presentation.ui.MainActivityViewModel
import kz.divtech.odyssey.drive.common.Variables
import kz.divtech.odyssey.drive.common.Variables.PaddingDp
import kz.divtech.odyssey.drive.presentation.theme.OdysseyDriveTheme
import kz.divtech.odyssey.drive.presentation.ui.components.OutlinedButton
import kz.divtech.odyssey.drive.presentation.ui.screens.main.CenterCircularProgressIndicator
import java.lang.StringBuilder

enum class Status{
    IN_PROGRESS, ON_THE_WAITING, ASSIGNED, COMPLETED, CANCELLED
}
@Composable
fun AssignmentDetailScreen(mainViewModel: MainActivityViewModel,
                           onPassengerListClicked: (seatCount:Int) -> Unit,
                           onCompleteClicked: (descText: String, taskTime: String) -> Unit,
                           taskId: Int,
                           viewModel: TaskDetailViewModel = hiltViewModel(),
                           snackbarHostState: SnackbarHostState
) {

    val isCancelVisible = remember { mutableStateOf(false) }
    val isCompleteClicked = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val onReturnBackClicked: () -> Unit = {
        isCancelVisible.value = false
    }

    val onCancelButtonClicked: (String) -> Unit = { reason ->
        viewModel.rejectTask(taskId, reason)
        isCancelVisible.value = false
    }

    val taskDetailState by viewModel.taskDetailState
    val task = taskDetailState.task

    val taskStatusState by viewModel.taskStatusState

    val taskText = stringResource(R.string.assignment)
    LaunchedEffect(Unit) {
        mainViewModel.setTitle("$taskText №${taskId}")
        viewModel.getTaskById(taskId)


    }
    if(taskDetailState.error.isNotBlank()){
        scope.launch {
            snackbarHostState.showSnackbar(taskDetailState.error)
        }
    }

    if(taskStatusState.error.isNotBlank()){
        scope.launch {
            snackbarHostState.showSnackbar(taskStatusState.error)
        }
    }
    OdysseyDriveTheme {
        if (isCancelVisible.value) {
            CancelScreen(onReturnBackClicked, onCancelButtonClicked, snackbarHostState)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    top = 24.dp,
                    start = PaddingDp,
                    end = PaddingDp,
                    bottom = PaddingDp
                )
        ) {


            if(task.status == Status.COMPLETED && isCompleteClicked.value){
                val descText = StringBuilder("№${task.id} выполнено ")
                if(task.isCompletedInTime) descText.append("вовремя")
                onCompleteClicked(descText.toString(), "${task.actualStartTime} - ${task.actualEndTime}")
            }else{
                AssignmentInfo(task)
            }

            if(task.status == Status.IN_PROGRESS){
                InProgressTimeCard(task)
            }else if(task.status == Status.CANCELLED){
                CancelReasonText(task.cancelReason)
            }

            if(taskDetailState.isLoading || taskStatusState.isLoading){
                CenterCircularProgressIndicator()
            }

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            when (task.status) {

                Status.ASSIGNED -> {
                    PassengerListButton(onPassengerListClicked, task)
                    CancelTaskButton { isCancelVisible.value = true }
                }

                Status.ON_THE_WAITING -> {
                    StartButton(viewModel, task)
                    PassengerListButton(onPassengerListClicked, task)
                    CancelTaskButton { isCancelVisible.value = true }
                }

                Status.IN_PROGRESS -> {
                    CompleteButton {
                        isCompleteClicked.value = true
                        viewModel.completeTask(task.id)
                    }
                    CancelTaskButton { isCancelVisible.value = true }
                    PassengerListButton(onPassengerListClicked, task)
                }
                
                Status.CANCELLED, Status.COMPLETED ->
                    PassengerListButton(onPassengerListClicked, task)

            }

        }
    }
}

@Composable
fun CancelReasonText(cancelReason: String){

    Text(
        text = stringResource(R.string.cancel_reason),
        modifier = Modifier
            .padding(top = 26.dp),
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            color = Color(0x80243254),
        )
    )

    Text(
        text = cancelReason,
        modifier = Modifier
            .padding(top = 6.dp),
        style = TextStyle(
            fontSize = 14.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight(600),
            color = Color(0xFFEF645B),
        )
    )
}
@Composable
fun StartButton(viewModel: TaskDetailViewModel, task: Task){
    Button(
        onClick = {
              viewModel.beginTask(task.id)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(Variables.ButtonHeight)
    ) {
        Text(text = stringResource(R.string.start_assignment))
    }
}

@Composable
fun CancelTaskButton(onRejectButtonClicked : () -> Unit) {
    OutlinedButton(
        text = stringResource(R.string.cancel_assignment),
        onClick = { onRejectButtonClicked() },
        modifier = Modifier
            .padding(top = 12.dp),
        color = Color(0xFFFF3E32)
    )
}


@Composable
fun CompleteButton(onCompleteClicked: () -> Unit){
    Button(
        onClick = {
            onCompleteClicked()
        },
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .height(Variables.ButtonHeight),
        colors = ButtonColors(
            containerColor = Color(0xFF52BF70),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFF52BF70),
            disabledContentColor = Color.White
        )
    ) {
        Text(text = stringResource(R.string.finish))
    }
}

@Composable
fun PassengerListButton(onPassengerListClicked: (seatCount: Int) -> Unit, task: Task){
    OutlinedButton(
        text = stringResource(R.string.passenger_list),
        onClick = { onPassengerListClicked(task.seatCount) },
        modifier = Modifier
            .padding(top = 12.dp)
    )
}

@Composable
fun InProgressTimeCard(task: Task){
    Card(modifier = Modifier
        .padding(top = 32.dp)
        .fillMaxWidth(),
        colors = CardColors(
            containerColor = Color(0x4DD9D9D9),
            contentColor = Color.Black,
            disabledContainerColor = Color(0x4DD9D9D9),
            disabledContentColor = Color.Black
        )
    ){
        Box(modifier = Modifier
            .padding(top = 12.dp, bottom = 9.dp, start = PaddingDp, end = PaddingDp)){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.you_started_assignment),
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF243254),
                        letterSpacing = 0.2.sp,
                    )
                )

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Text(
                            text = stringResource(R.string.started).uppercase(),
                            style = TextStyle(
                                fontSize = 11.sp,
                                lineHeight = 16.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0x4D243254),
                                letterSpacing = 0.22.sp,
                            )
                        )
                        Text(
                            text = task.actualStartTime,
                            modifier = Modifier
                                .padding(top = 2.dp),
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF243254),
                                letterSpacing = 0.2.sp,
                            )
                        )
                    }

                    Text(
                        text = " — ",
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF243254),
                            letterSpacing = 0.2.sp,
                        )
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(R.string.end).uppercase(),
                            modifier = Modifier
                                .padding(top = 2.dp),
                            style = TextStyle(
                                fontSize = 11.sp,
                                lineHeight = 16.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0x4D243254),
                                letterSpacing = 0.22.sp,
                            )
                        )

                        Text(
                            text = "00:00",
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0x4D000000),
                                letterSpacing = 0.2.sp,
                            )
                        )


                    }
                }
            }
        }
    }
}


