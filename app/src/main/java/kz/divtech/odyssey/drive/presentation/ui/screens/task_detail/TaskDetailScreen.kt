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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.presentation.ui.MainActivityViewModel
import kz.divtech.odyssey.drive.common.Variables
import kz.divtech.odyssey.drive.common.Variables.PaddingDp
import kz.divtech.odyssey.drive.presentation.theme.OdysseyDriveTheme
import kz.divtech.odyssey.drive.presentation.ui.components.OutlinedButton

enum class Status{
    IN_PROGRESS, ON_THE_WAITING, ASSIGNED, COMPLETED, CANCELLED
}
@Composable
@Preview
fun getAssignmentDetailScreenByStatus() {
    AssignmentDetailScreen(mainViewModel = viewModel(),
        taskId = 148,
        onFinishedClicked = {},
        onPassengerListClicked = {})
}

@Composable
fun AssignmentDetailScreen(mainViewModel: MainActivityViewModel,
                           onPassengerListClicked: () -> Unit,
                           onFinishedClicked: () -> Unit,
                           taskId: Int,
                           viewModel: TaskDetailViewModel = hiltViewModel()
) {

    var cancelReason = ""
    val isCancelVisible = remember { mutableStateOf(false) }

    val onReturnBackClicked: () -> Unit = {
        isCancelVisible.value = false
    }

    val onCancelButtonClicked: (String) -> Unit = { reason ->
        cancelReason = reason
        isCancelVisible.value = false
    }

    var task = viewModel.taskDetailState.value.task
    val taskText = stringResource(R.string.assignment)
    LaunchedEffect(Unit) {
        mainViewModel.setTitle("$taskText №${taskId}")
        viewModel.getTaskById(taskId)
    }

    OdysseyDriveTheme {
        if (isCancelVisible.value) {
            CancelScreen(onReturnBackClicked, onCancelButtonClicked)
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
            AssignmentInfo(task = task)

            if (task.status == Status.CANCELLED) {

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

            InProgressTimeCard(task)

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            if (task.status == Status.ON_THE_WAITING) {
                Button(
                    onClick = {
                        task = task.copy(status = Status.IN_PROGRESS)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Variables.ButtonHeight)
                ) {
                    Text(text = stringResource(R.string.start_assignment))
                }
            }

            if (task.status == Status.IN_PROGRESS) {
                Button(
                    onClick = {
                        onFinishedClicked()
                        task = task.copy(status = Status.COMPLETED)
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

                OutlinedButton(
                    text = stringResource(R.string.cancel),
                    onClick = {
                        task = task.copy(status = Status.ON_THE_WAITING)
                    },
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
            }

            OutlinedButton(
                text = stringResource(R.string.passenger_list),
                onClick = { onPassengerListClicked() },
                modifier = Modifier
                    .padding(top = 12.dp)
            )

            if (task.status == Status.ASSIGNED ||
                task.status == Status.ON_THE_WAITING
            ) {
                OutlinedButton(
                    text = stringResource(R.string.cancel_assignment),
                    onClick = { isCancelVisible.value = true },
                    modifier = Modifier
                        .padding(top = 12.dp),
                    color = Color(0xFFFF3E32)
                )
            }
        }
    }
}


@Composable
fun InProgressTimeCard(task: Task){
    if(task.status == Status.IN_PROGRESS){
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
                                text = "12:25",
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
                                text = "12:25",
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
}


