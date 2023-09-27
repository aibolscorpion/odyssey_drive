package kz.divtech.odyssey.drive.ui.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.ui.Variables
import kz.divtech.odyssey.drive.ui.Variables.PaddingDp
import kz.divtech.odyssey.drive.ui.theme.OdysseyDriveTheme

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(onLogin = {})
}

@Composable
fun LoginScreen(onLogin: () -> Unit){
    OdysseyDriveTheme {
        val phoneNumber = remember{mutableStateOf(TextFieldValue())}
        val password = remember{mutableStateOf(TextFieldValue())}

        Column(modifier = Modifier
            .fillMaxSize()
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
                    color = Color(0x4D243254),
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
                    enterAccount(onLogin, phoneNumber.value.text, password.value.text)
                },
                modifier = Modifier
                    .padding(top = PaddingDp)
                    .fillMaxWidth()
                    .height(Variables.ButtonHeight)){
                Text(stringResource(R.string.enter))
            }
        }
    }
}

fun enterAccount(onLogin: () -> Unit, phoneNumber: String, password: String){
    if(phoneNumber.isNotEmpty() && password.isNotEmpty()){
        onLogin()
    }
}


