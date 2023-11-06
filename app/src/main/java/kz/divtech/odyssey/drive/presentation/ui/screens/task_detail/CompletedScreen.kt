package kz.divtech.odyssey.drive.presentation.ui.screens.task_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.common.Variables
import kz.divtech.odyssey.drive.presentation.theme.OdysseyDriveTheme
import kz.divtech.odyssey.drive.presentation.ui.components.OutlinedButton

@Preview
@Composable
fun CompletedScreenPreview(){
    CompletedScreen("№1 Выполнение вовремя",
        "00:00 - 00:00",
        onAssignmentListClicked = {})
}

@Composable
fun CompletedScreen(descriptionText: String,
                    taskTime: String,
                    onAssignmentListClicked: () -> Unit){

    OdysseyDriveTheme {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(all = Variables.PaddingDp)) {

            Spacer(modifier = Modifier
                .weight(1f))

            Column(modifier = Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally){
                Icon(painter = painterResource(R.drawable.icon_completed_status),
                    contentDescription = null,
                    modifier = Modifier
                        .background(color = Color(0xFF36B959), shape = CircleShape)
                        .padding(all = Variables.PaddingDp),
                    tint = Color.White)


                Text(
                    stringResource(R.string.assignment_completed),
                    modifier = Modifier
                        .padding(top = 28.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF36B959),
                        letterSpacing = 0.2.sp,
                    )
                )

                Text(
                    text = descriptionText,
                    modifier = Modifier
                        .padding(top = 7.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF243254),
                    )
                )

                Text(
                    text = taskTime,
                    modifier = Modifier
                        .padding(top = Variables.PaddingDp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        letterSpacing = 0.2.sp,
                    )
                )
            }

            Spacer(modifier = Modifier
                .weight(1f))

            OutlinedButton(
                text = stringResource(R.string.to_the_list_of_assignments),
                onClick = { onAssignmentListClicked() },
                color = Color(0xFF3291FF),
                modifier = Modifier
                    .padding(top = 12.dp))

        }
    }
}


