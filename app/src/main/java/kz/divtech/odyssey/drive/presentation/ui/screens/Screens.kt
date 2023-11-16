package kz.divtech.odyssey.drive.presentation.ui.screens

import kz.divtech.odyssey.drive.R

enum class Screens {
    LOADING,
    LOGIN,
    ASSIGNMENT_DETAILS,
    LIST_OF_PASSENGERS,
    ASSIGNMENT_COMPLETED
}

sealed class BottomNavItem(val title: Int, var icon: Int, val screen_route: String){
    object Main: BottomNavItem(R.string.main, R.drawable.icon_home, "MAIN")
    object MyAssignments: BottomNavItem(R.string.my_assignments, R.drawable.icon_calendar, "MY_ASSIGNMENTS")
    object Profile: BottomNavItem(R.string.profile, R.drawable.icon_profile, "PROFILE")
}
