package kz.divtech.odyssey.drive.ui

import kz.divtech.odyssey.drive.R

enum class Screens {
    LOGIN,
    ASSIGNMENT_DETAILS,
    LIST_OF_PASSENGERS
}

sealed class BottomNavItem(val title: Int, var icon: Int, val screen_route: String){
    object Home: BottomNavItem(R.string.home, R.drawable.icon_home, "home")
    object MyAssignments: BottomNavItem(R.string.my_assignments, R.drawable.icon_calendar, "my_assignments")
    object Profile: BottomNavItem(R.string.profile, R.drawable.icon_profile, "profile")
}
