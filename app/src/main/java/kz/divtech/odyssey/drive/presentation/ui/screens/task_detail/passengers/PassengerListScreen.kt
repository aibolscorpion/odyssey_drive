package kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.passengers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
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
import kz.divtech.odyssey.drive.domain.model.task_detail.Passenger
import kz.divtech.odyssey.drive.presentation.theme.OdysseyDriveTheme
import kz.divtech.odyssey.drive.presentation.ui.screens.main.CenterCircularProgressIndicator

@Preview(showSystemUi = true)
@Composable
fun PassengerListScreenPreview(){
    PassengerListScreen(mainViewModel = viewModel(), 2, 4, snackBarHostState = SnackbarHostState())
}


@Composable
fun PassengerListScreen(mainViewModel: MainActivityViewModel,
                        taskId: Int,
                        seatCount: Int,
                        passengersViewModel: PassengersViewModel = hiltViewModel(),
                        snackBarHostState: SnackbarHostState){

    val scope = rememberCoroutineScope()
    val passengersState by passengersViewModel.passengersState
    val passengerList = passengersState.passengerList

    if(passengersState.error.isNotBlank()){
        scope.launch {
            snackBarHostState.showSnackbar(passengersState.error)
        }
    }

    OdysseyDriveTheme {
        val screenTitle = stringResource(R.string.passenger_list)

        LaunchedEffect(Unit){
            mainViewModel.setTitle(screenTitle)
            passengersViewModel.getPassengerList(taskId)
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = PaddingDp, start = PaddingDp, end = PaddingDp, bottom = 32.dp)) {

            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF243254),
                    letterSpacing = 0.2.sp,
                )){
                    append(stringResource(R.string.passengers))
                }
                withStyle(style = SpanStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0x66243254),
                    letterSpacing = 0.2.sp,
                )){
                    append(" ${passengerList.size}")
                }
            })



            LazyColumn(modifier = Modifier
                .padding(top = PaddingDp)) {

                itemsIndexed(items = seatList(seatCount, passengerList)){ index, fullName ->
                    PassengerItem(index+1, fullName)
                }
            }

            if(passengersState.isLoading){
                CenterCircularProgressIndicator()
            }
        }

    }
}

fun seatList(seatCount: Int, passengerList: List<Passenger>): List<String?> {
    return (1..seatCount).map { seatNumber ->
        passengerList.find { passenger -> passenger.seatNumber == seatNumber }?.fullName
    }
}

@Composable
fun PassengerItem(seatNumber: Int, passengerFullName: String?){
    Column {
        Row(modifier = Modifier
            .padding(top = PaddingDp, bottom = PaddingDp)){

            Text(text = "№$seatNumber",
                modifier=Modifier
                    .fillMaxWidth(0.11f),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0x66243254),
                )
            )
            if(passengerFullName != null){
                Text(text = passengerFullName,
                    modifier = Modifier
                        .padding(start = 7.dp)
                        .fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF243254),

                        )
                )
            }else{
                Text(
                    text = stringResource(R.string.free),
                    modifier = Modifier
                        .padding(start = 7.dp)
                        .fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFB7BDCA)
                    )
                )
            }
        }

        HorizontalDivider()
    }
}