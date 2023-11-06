package kz.divtech.odyssey.drive.presentation.ui.screens.profile

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.presentation.ui.MainActivityViewModel
import kz.divtech.odyssey.drive.common.Variables
import kz.divtech.odyssey.drive.presentation.theme.BottomBorder
import kz.divtech.odyssey.drive.presentation.theme.OdysseyDriveTheme
import kz.divtech.odyssey.drive.domain.model.profile.Profile
import kz.divtech.odyssey.drive.presentation.ui.components.OutlinedButton
import kz.divtech.odyssey.drive.presentation.ui.screens.BottomNavItem
import kz.divtech.odyssey.drive.presentation.ui.screens.main.CenterCircularProgressIndicator

@Preview
@Composable
fun ProfileScreenPreview(){
    ProfileScreen(mainViewModel = viewModel(), profileViewModel = viewModel(), SnackbarHostState())
}

@Composable
fun ProfileScreen(mainViewModel: MainActivityViewModel,
                  profileViewModel: ProfileViewModel = hiltViewModel(),
                snackbarHostState: SnackbarHostState) {

    val scope = rememberCoroutineScope()
    val profileState = profileViewModel.profileState.value
    val logoutState = profileViewModel.logoutState.value

    val profile: Profile = profileState.profile
    if(profileState.error.isNotBlank()) {
        scope.launch {
            snackbarHostState.showSnackbar(profileState.error)
        }
    }

    if(logoutState.error.isNotBlank()){
        scope.launch {
            snackbarHostState.showSnackbar(logoutState.error)
        }
    }

    OdysseyDriveTheme {
        val screenTitle = stringResource(BottomNavItem.Profile.title)
        LaunchedEffect(Unit){
            mainViewModel.setTitle(screenTitle)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 28.dp)
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

            KeyValueText(key = R.string.surname_name_patronymic, value = "${profile.surname} ${profile.name} ${profile.patronymic}")
            KeyValueText(key = R.string.vehicle, value = profile.vehicleName )
            KeyValueText(key = R.string.car_number, value = profile.stateNumber )
            KeyValueText(key = R.string.number_of_seats, value = profile.seatCount.toString() )

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                text = stringResource(R.string.logout),
                onClick = {
                    profileViewModel.logout()
                }
            )
        }

        if(profileState.isLoading || logoutState.isLoading){
            CenterCircularProgressIndicator()
        }
    }
}

@Composable
fun KeyValueText(modifier: Modifier = Modifier,
                 @StringRes key: Int,
                 value: String = "",
                 valueComposable: @Composable () -> Unit = {
                     Text(
                         text = value,
                         style = TextStyle(
                             fontSize = 14.sp,
                             fontWeight = FontWeight(450),
                             color = Color(0xFF243254),
                             textAlign = TextAlign.Right,
                         )
                     )
                 } ){
    Column(modifier = modifier) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = Variables.SpacingM, bottom = Variables.SpacingM),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = stringResource(key),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0x80243254),
                )
            )

            valueComposable()

        }

        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(),
            color = BottomBorder
        )
    }
}