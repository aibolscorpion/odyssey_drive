package kz.divtech.odyssey.drive.presentation.ui.screens.task_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.domain.model.DataSource
import kz.divtech.odyssey.drive.presentation.ui.MainActivityViewModel
import kz.divtech.odyssey.drive.common.Variables.PaddingDp
import kz.divtech.odyssey.drive.presentation.theme.OdysseyDriveTheme

@Preview(showSystemUi = true)
@Composable
fun PassengerListScreenPreview(){
    PassengerListScreen(mainViewModel = viewModel())
}


@Composable
fun PassengerListScreen(mainViewModel: MainActivityViewModel){
    OdysseyDriveTheme {
        val screenTitle = stringResource(R.string.passenger_list)
        LaunchedEffect(Unit){
            mainViewModel.setTitle(screenTitle)
        }


        Column(modifier = Modifier
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
                    append(" ${DataSource.passengerList.size}")
                }
            })

            LazyColumn(modifier = Modifier
                .padding(top = PaddingDp)) {
                itemsIndexed(items = DataSource.passengerList){ index, text ->
                    PassengerItem(index, text)
                }
            }
        }

    }
}

@Composable
fun PassengerItem(index: Int, name: String){
    Column {
        Row(modifier = Modifier
            .padding(top = PaddingDp, bottom = PaddingDp)){

            Text(text = "№$index",
                modifier=Modifier
                    .fillMaxWidth(0.11f),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0x66243254),
                )
            )

            Text(text = name,
                modifier = Modifier
                    .padding(start = 7.dp)
                    .fillMaxWidth(),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF243254),

                )
            )
        }

        HorizontalDivider()
    }
}