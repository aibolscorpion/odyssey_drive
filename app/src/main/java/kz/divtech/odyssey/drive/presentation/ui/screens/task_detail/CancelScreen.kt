package kz.divtech.odyssey.drive.presentation.ui.screens.task_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.common.Variables
import kz.divtech.odyssey.drive.presentation.theme.OdysseyDriveTheme
import kz.divtech.odyssey.drive.presentation.ui.components.OutlinedButton

@Preview(showSystemUi = true)
@Composable
fun CancelScreenPreview(){
    CancelScreen(onReturnButtonClicked = {}, onCancelButtonClicked = {})
}

@Composable
fun CancelScreen(onReturnButtonClicked: () -> Unit, onCancelButtonClicked: (String) -> Unit){
    OdysseyDriveTheme {
        val cancelReason = remember { mutableStateOf(TextFieldValue()) }

        Dialog(onDismissRequest = {}){
            Card {

                Column(modifier = Modifier
                    .padding(top = 35.dp, bottom = 32.dp, start = 12.dp, end = 12.dp)) {
                    Text(
                        text = stringResource(R.string.cancel_assignment),
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF243254),
                            letterSpacing = 0.2.sp,
                        )
                    )

                    OutlinedTextField(
                        value = cancelReason.value,
                        onValueChange = { cancelReason.value = it },
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .fillMaxWidth()
                            .height(200.dp),
                        placeholder = {

                            Text(
                                text = stringResource(R.string.fill_cancel_reason),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF243254),
                                )
                            )
                        }
                    )

                    Button(onClick = { onCancelButtonClicked(cancelReason.value.text) },
                        modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                        .height(Variables.ButtonHeight),
                    colors = ButtonColors(
                        containerColor = Color(0xFFFF564C),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFFF564C),
                        disabledContentColor = Color.White)
                    ){

                        Text(
                            text = stringResource(R.string.cancel_assignment),
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight(600),
                                color = Color.White,
                            )
                        )
                    }

                    OutlinedButton(text = stringResource(R.string.return_back),
                        color = Color(0xFF243254),
                        onClick = { onReturnButtonClicked() },
                        modifier = Modifier
                            .padding(top = 8.dp))
                }
            }
        }
    }
}