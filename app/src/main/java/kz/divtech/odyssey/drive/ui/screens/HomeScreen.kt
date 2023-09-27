package kz.divtech.odyssey.drive.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.ui.BottomNavItem
import kz.divtech.odyssey.drive.ui.MainViewModel
import kz.divtech.odyssey.drive.ui.Variables
import kz.divtech.odyssey.drive.ui.Variables.PaddingDp
import kz.divtech.odyssey.drive.ui.theme.BottomBorder
import kz.divtech.odyssey.drive.ui.theme.ColorPrimary
import kz.divtech.odyssey.drive.ui.theme.ColorTypoSecondary
import kz.divtech.odyssey.drive.ui.theme.OdysseyDriveTheme

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen(mainViewModel = viewModel(), onAssignmentClick = {})
}

@Composable
fun HomeScreen(mainViewModel: MainViewModel, onAssignmentClick: (id: Int) -> Unit) {
    val driverName = "Баталгазиев Р.В."
    val driverCar = "M-Benz Sprinter 2014"
    val carNumber = "902 XNA 02"

    val allAssignmentsCount = 32
    val completedAssignmentsCount = 0
    val cancelledAssignmentCount = 0


    OdysseyDriveTheme {
        val screenTitle = stringResource(BottomNavItem.Home.title)
        LaunchedEffect(Unit){
            mainViewModel.setTitle(screenTitle)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)){

            DriverInfo(driverName, driverCar, carNumber)

            AssignmentForTodayCard(allAssignmentsCount, completedAssignmentsCount, cancelledAssignmentCount)

            NearestAssignment(onAssignmentClick)

            Spacer(modifier = Modifier
                .weight(1f))

            Buttons()
        }
    }



}

@Composable
fun DriverInfo(driverName: String, carModel: String, carNumber: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {

            Text(
                text = driverName,
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF0F1830),
                    letterSpacing = 0.2.sp,
                )
            )

            Text(
                text = "$carModel * $carNumber",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0x4D233236),
                ),
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }

       NotificationCount(0)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationCount(count: Int){
    BadgedBox(
        badge = {
            Badge(
                modifier = Modifier.offset(x = (-10).dp, y = 7.dp),
                containerColor = Color(0xFFFB4444)
            ) {
                Text(text = "$count",
                    fontSize = 15.sp,
                    fontWeight = FontWeight(600),
                    color = Color.White
                )
            }
        }) {
        Icon( painterResource(R.drawable.icon_bell), contentDescription = "notifications")

    }
}

@Composable
fun AssignmentForTodayCard(all: Int, completed: Int, cancelled: Int){
    val todayDate = "24 сентября, вторник"
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp)
        .border(width = 1.dp, color = BottomBorder, shape = RoundedCornerShape(8.dp))
    ) {
        Box(modifier = Modifier
            .padding(top = 14.dp, start = PaddingDp, end = PaddingDp, bottom = PaddingDp)){
            Column {
                Text(text = stringResource(R.string.assigment_for_today),
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        color = Color.Black,
                )
                )

                Text(modifier = Modifier
                    .padding(top = 4.dp),
                    text = todayDate,
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF233236),
                    ))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = PaddingDp),
                    horizontalArrangement = Arrangement.SpaceAround) {

                    AssignmentCountTV(R.string.all, all, Color.Black)

                    AssignmentCountTV(R.string.completed, completed, Color(0xFF70CD8A))

                    AssignmentCountTV(R.string.cancelled, cancelled, color = Color(0x52000000))
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
            style = TextStyle(
                fontSize = 13.sp,
                lineHeight = 16.sp,
                color = Color(0x80233236),
            ))

    }
}

@Composable
fun NearestAssignment(onAssignmentClick: (id: Int) -> Unit){
    val nearestAssignmentTime = "08:00 – 20:00"
    val stationNames = "Вокзал «Актогай» — ГОК"
    val assigmentId = 163
    val peopleNumber = 14
    val assigmentStatus = "В работе"
    Text(text = stringResource(R.string.nearest_assignment),
        modifier = Modifier
            .padding(top = PaddingDp, bottom = PaddingDp),
        style = TextStyle(
            fontSize = 18.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight(600),
            color = Color.Black,
        ))

    SingleAssignment(
        onAssignmentClick = onAssignmentClick,
        nearestAssignmentTime,
        stationNames,
        assigmentId,
        peopleNumber,
        assigmentStatus
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleAssignment(onAssignmentClick: (id: Int) -> Unit,
                     time: String,
                     departureArrivalPlace: String,
                     assignmentNumber: Int,
                     peopleNumber: Int,
                     status: String){

    Card(modifier = Modifier
        .fillMaxWidth()
        .border(width = 1.dp, color = BottomBorder, shape = RoundedCornerShape(8.dp)),
        onClick = {
            onAssignmentClick(assignmentNumber)
        }
    ) {
        Box(modifier = Modifier
            .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = PaddingDp)){
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = time,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(600),
                            color = Color.Black,
                        ))

                    Text(
                        text = status,
                        modifier = Modifier
                            .background(color = Color(0xFFE8F2FF), shape = RoundedCornerShape(16.dp))
                            .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(600),
                            color = ColorPrimary,
                        )
                    )
                }

                Text(text = departureArrivalPlace,
                    modifier = Modifier
                        .padding(top = 10.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        color = Color.Black,
                    ))

                Text(modifier = Modifier
                    .padding(top = 6.dp),
                    text = "$assignmentNumber * $peopleNumber ${stringResource(R.string.human)}",
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
fun Buttons() {
    val time = "00:15"
    var showStartButton by remember { mutableStateOf(true) }
    if (showStartButton) {
        Button(
            onClick = {
                showStartButton = false
                startShift()
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
    } else {
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

            Text(text = " $time",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                ))
        }


        Button(
            onClick = {
                pauseShift()
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
                    .padding(start = 8.dp)
            )
        }


        OutlinedButton(
            onClick = {
                showStartButton = true
                closeShift()
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(Variables.ButtonHeight))
        {
            Text(text = stringResource(R.string.finish_shift))
        }
    }
}
fun startShift(){
}

fun pauseShift(){

}

fun closeShift(){

}
