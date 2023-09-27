package kz.divtech.odyssey.drive.ui.screens.my_assignments

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.ui.MainViewModel
import kz.divtech.odyssey.drive.ui.Variables
import kz.divtech.odyssey.drive.ui.Variables.PaddingDp
import kz.divtech.odyssey.drive.ui.screens.KeyValueText
import kz.divtech.odyssey.drive.ui.theme.OdysseyDriveTheme

@Preview
@Composable
fun AssignmentDetailScreenPreview(){
    AssignmentDetailScreen(mainViewModel = viewModel())
}

@Composable
fun AssignmentDetailScreen(mainViewModel: MainViewModel) {
    OdysseyDriveTheme {
        val assignment = stringResource(R.string.assigment)
        val assignmentId = "00163"
        val stationNames = "Вокзал «Актогай» — ГОК"
        val assignmentStatus = "Назначено"
        val assignmentDate = "23 сент, вт (сегодня)"
        val planTime = "12:30 – 14:00"
        val passengerAmount = 17

        LaunchedEffect(Unit){
            mainViewModel.setTitle("$assignment №$assignmentId")
        }
        Column(modifier = Modifier
            .padding(top = 24.dp, start = PaddingDp, end = PaddingDp, bottom = PaddingDp)) {
            Text(text = stationNames,
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF243254),
                    letterSpacing = 0.2.sp,
                )
            )

            KeyValueText(R.string.status, assignmentStatus, modifier = Modifier
                .padding(top = PaddingDp))
            KeyValueText(R.string.id, assignmentId)
            KeyValueText(R.string.date, assignmentDate)
            KeyValueText(R.string.plan_time, planTime)
            KeyValueText(R.string.passanger_amount, passengerAmount.toString())

            Card {

            }
            Spacer(modifier = Modifier
                .weight(1f))

            Button(onClick = {startAssignment()},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Variables.ButtonHeight)
            ){
                Text(text = stringResource(R.string.start_assignment))
            }

            OutlinedButton(onClick = { listOfPassengers() },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .height(Variables.ButtonHeight)){
                Text(text = stringResource(R.string.passanger_list))
            }

            OutlinedButton(onClick = { cancelAssignment() },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .height(Variables.ButtonHeight),
                border = BorderStroke(1.dp, Color(0xFFFF3E32))
            ){
                Text(text = stringResource(R.string.cancel_assignment),
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFF3E32),
                    )
                )
            }
        }
    }
}

@Composable
fun AssignmentCompleted(){
    Column(modifier = Modifier
        .fillMaxSize()) {

        Image(painter = painterResource(R.drawable.icon_calendar), contentDescription = null)

        Text("Задание завершено")

    }
}


fun startAssignment(){}

fun cancelAssignment(){}

fun listOfPassengers(){}
