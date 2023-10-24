package kz.divtech.odyssey.drive.presentation.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.common.Variables
import kz.divtech.odyssey.drive.common.Variables.PaddingDp
import kz.divtech.odyssey.drive.presentation.theme.GreyAlpha30
import kz.divtech.odyssey.drive.presentation.theme.OdysseyDriveTheme
import kz.divtech.odyssey.drive.presentation.ui.screens.main.CenterCircularProgressIndicator

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(viewModel(), openMainScreen = {})
}


@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), openMainScreen: () -> Unit){

    val context = LocalContext.current
    val state = viewModel.state.value

    if(state.loggedIn){
        openMainScreen()
    }

    if(state.error.isNotBlank()) {
        Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
    }

    OdysseyDriveTheme {
        val phoneNumber = remember{mutableStateOf(TextFieldValue())}
        val password = remember{mutableStateOf(TextFieldValue())}

        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFEBEDF0))
            .padding(start = PaddingDp, end = PaddingDp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

            Text(stringResource(R.string.enter_account),
                modifier = Modifier
                    .fillMaxWidth(),
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF243254),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp,
                )
            )

            Text(stringResource(R.string.enter_your_phone_number_and_password),
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(500),
                    color = GreyAlpha30,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .padding(top = PaddingDp))


            OutlinedTextField(value = phoneNumber.value,
                onValueChange = { phoneNumber.value = it },
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = {
                    Text(stringResource(R.string.phone_number))
                },
                prefix = {
                    Text(stringResource(R.string.phone_number_prefix))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone,
                        contentDescription = stringResource(R.string.phone_number))
                }
            )


            OutlinedTextField(value = password.value,
                onValueChange = { password.value = it },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = {
                    Text(stringResource(R.string.password))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock,
                        contentDescription = stringResource(R.string.password))
                }
            )


            Button(
                onClick = {
                    login(viewModel, phoneNumber.value.text, password.value.text)
                },
                modifier = Modifier
                    .padding(top = PaddingDp)
                    .fillMaxWidth()
                    .height(Variables.ButtonHeight)){
                Text(
                    text = stringResource(R.string.enter),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        color = Color.White,
                    )
                )
            }
        }

        if(state.isLoading){
            CenterCircularProgressIndicator()
        }
    }
}

fun login(viewModel: LoginViewModel, phoneNumber: String, password: String){
    if(phoneNumber.isNotEmpty() && password.isNotEmpty()){
        viewModel.login(phoneNumber, password)
    }
}


