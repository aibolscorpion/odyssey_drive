package kz.divtech.odyssey.drive.presentation.ui.screens.task_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.domain.model.DataSource
import kz.divtech.odyssey.drive.presentation.ui.screens.profile.KeyValueText

@Preview
@Composable
fun AssignmentInfoPreview(){
    AssignmentInfo(DataSource.task)
}

@Composable
fun AssignmentInfo(task: Task){
    Column {
        Text(text = task.departureArrivalPlace,
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF243254),
                letterSpacing = 0.2.sp,
            )
        )

        KeyValueText(key = R.string.status, valueComposable = { StatusText(task.status) })
        if(task.status == Status.CANCELLED){
            KeyValueText(key = R.string.cancelled, value = task.time) }
        KeyValueText(key = R.string.id, value = task.id.toString())
        KeyValueText(key = R.string.date, value = task.date)
        when (task.status) {
            Status.COMPLETED -> {
                KeyValueText(key = R.string.plan_time, value = task.time)
                KeyValueText(key = R.string.actual_time,
                    valueComposable = { BoldText(task.time) })
            }

            Status.CANCELLED -> {
                KeyValueText(key = R.string.plan_time, value = task.time)
            }

            else -> KeyValueText(key = R.string.plan_time,
                valueComposable = { BoldText(task.time) })

        }
        KeyValueText(key = R.string.passenger_amount, value = "${task.passengerCount} чел.")
    }
}


@Composable
fun StatusText(status: Status){
    val statusText: String
    val backgroundColor: Color
    val textColor: Color
    when(status){
        Status.ASSIGNED -> {
            statusText = stringResource(R.string.assigned)
            textColor = Color(0x800B276D)
            backgroundColor = Color(0xFFF0F0F0)
        }
        Status.ON_THE_WAITING -> {
            statusText = stringResource(R.string.on_the_waiting)
            textColor = Color(0xFFC5951C)
            backgroundColor = Color(0xFFFFFBE8)
        }
        Status.IN_PROGRESS -> {
            statusText = stringResource(R.string.in_progress)
            textColor = Color(0xFF3291FF)
            backgroundColor = Color(0xFFE8F2FF)
        }
        Status.COMPLETED -> {
            statusText = stringResource(R.string.completed).replaceFirstChar {
                it.titlecase()
            }
            textColor = Color.White
            backgroundColor = Color(0xFF68A679)
        }
        Status.CANCELLED -> {
            statusText = stringResource(R.string.cancelled).replaceFirstChar {
                it.titlecase()
            }
            textColor = Color.White
            backgroundColor = Color(0xFFEF645B)
        }
    }

    Text(
        text = statusText,
        modifier = Modifier
            .background(backgroundColor, CircleShape)
            .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),
        style = TextStyle(
            fontSize = 14.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight(600),
            color = textColor,
        )
    )
}

@Composable
fun BoldText(time: String){
    Text(
        text = time,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight(600),
            color = Color(0xFF243254)
        )
    )
}