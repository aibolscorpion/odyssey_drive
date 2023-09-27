package kz.divtech.odyssey.drive.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import kz.divtech.odyssey.drive.ui.theme.BottomBorder
import kz.divtech.odyssey.drive.ui.theme.GreyText
import kz.divtech.odyssey.drive.ui.theme.OdysseyDriveTheme


@Preview
@Composable
fun ProfileScreenPreview(){
    ProfileScreen(mainViewModel = viewModel(), onLogout = {})
}

@Composable
fun ProfileScreen(mainViewModel: MainViewModel, onLogout: () -> Unit) {
    val driverName = "Баталгазиев Р.В."
    val workingHours = "08:00 - 20:00"
    val vehicle = "M-Benz Sprinter 2014"
    val carNumber = "902 KNN 02"
    val numberOfSeats = 18

    OdysseyDriveTheme {
        val screenTitle = stringResource(BottomNavItem.Profile.title)
        LaunchedEffect(Unit){
            mainViewModel.setTitle(screenTitle)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 28.dp),
        ) {

            Text(
                modifier = Modifier
                    .padding(bottom = 24.dp),
                text = stringResource(R.string.my_profile),
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                    letterSpacing = 0.2.sp,
                )
            )

            KeyValueText(R.string.surname_name_patronymic, driverName)
            KeyValueText(R.string.working_hours, workingHours)
            KeyValueText(R.string.vehicle, vehicle )
            KeyValueText(R.string.car_number, carNumber )
            KeyValueText(R.string.number_of_seats, numberOfSeats.toString() )

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = { onLogout() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Text(stringResource(R.string.logout))
            }
        }
    }
}

@Composable
fun KeyValueText(@StringRes key: Int, value: String, modifier: Modifier = Modifier){
    Column(modifier = modifier) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = Variables.SpacingM, bottom = Variables.SpacingM),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Text(
                text = stringResource(key),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = GreyText,
                )
            )

            Text(
                text = value,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(450),
                    color = Color.Black,
                    textAlign = TextAlign.Right,
                )
            )

        }

        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(),
            color = BottomBorder
        )
    }
}