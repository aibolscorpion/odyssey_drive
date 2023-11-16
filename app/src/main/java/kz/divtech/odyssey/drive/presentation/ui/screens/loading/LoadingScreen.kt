package kz.divtech.odyssey.drive.presentation.ui.screens.loading

import androidx.compose.runtime.Composable
import kz.divtech.odyssey.drive.presentation.theme.OdysseyDriveTheme
import kz.divtech.odyssey.drive.presentation.ui.screens.main.CenterCircularProgressIndicator

@Composable
fun LoadingScreen() {
    OdysseyDriveTheme {
        CenterCircularProgressIndicator()
    }
}