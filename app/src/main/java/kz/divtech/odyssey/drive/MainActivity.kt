package kz.divtech.odyssey.drive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kz.divtech.odyssey.drive.ui.BottomNavItem
import kz.divtech.odyssey.drive.ui.MainViewModel
import kz.divtech.odyssey.drive.ui.Screens
import kz.divtech.odyssey.drive.ui.screens.HomeScreen
import kz.divtech.odyssey.drive.ui.screens.MyAssignmentsScreen
import kz.divtech.odyssey.drive.ui.screens.ProfileScreen
import kz.divtech.odyssey.drive.ui.screens.LoginScreen
import kz.divtech.odyssey.drive.ui.screens.my_assignments.AssignmentDetailScreen
import kz.divtech.odyssey.drive.ui.screens.my_assignments.PassengerListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            MainScreenView()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenView(){
    val mainViewModel: MainViewModel = viewModel()
    val title: String by mainViewModel.title.observeAsState(stringResource(R.string.app_name))

    val navController = rememberNavController()



    val bottomBarState = rememberSaveable{ mutableStateOf(false) }
    val topBarState = rememberSaveable{ mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when(navBackStackEntry?.destination?.route){
        Screens.LOGIN.name-> {
            bottomBarState.value = false
            topBarState.value = false
        }
        else -> {
            bottomBarState.value = true
            topBarState.value = true
        }
    }

    val canNavigateBack: Boolean = when(navBackStackEntry?.destination?.route){
        BottomNavItem.Home.screen_route,
        BottomNavItem.Profile.screen_route,
        BottomNavItem.MyAssignments.screen_route -> false

        else -> true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                isVisible = topBarState.value,
                title = title,
                canNavigateBack = canNavigateBack,
                navigateUp = {
                    navController.navigateUp()
                })
                 },
        bottomBar = {
            BottomNavigation(navController = navController, isVisible = bottomBarState.value)
        }
    ){ padding ->
        Box(modifier = Modifier.padding(padding)){
            NavigationGraph(mainViewModel, navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(isVisible: Boolean, title: String, canNavigateBack: Boolean = false, navigateUp: () -> Unit){
    AnimatedVisibility(isVisible){
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600))
            },
            navigationIcon = {
                if(canNavigateBack){
                    IconButton(onClick = navigateUp ){
                        Icon(imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            },
            colors = TopAppBarColors(
                containerColor = Color.White,
                scrolledContainerColor = Color.Green,
                navigationIconContentColor = Color.Black,
                titleContentColor = Color.Black,
                actionIconContentColor = Color.Black
            )
        )
    }
}

@Composable
fun NavigationGraph(mainViewModel: MainViewModel, navController: NavHostController) {
    NavHost(navController, startDestination = Screens.LOGIN.name) {

        composable(Screens.LOGIN.name) {
            val onLogin = {
                navController.navigate(BottomNavItem.Home.screen_route) {
                    popUpTo(0)
                }
            }
            LoginScreen(onLogin)
        }

        val onAssignmentClick : (id: Int) -> Unit = { id ->
            navController.navigate(Screens.ASSIGNMENT_DETAILS.name)
        }

        composable(BottomNavItem.Home.screen_route) {
            HomeScreen(mainViewModel, onAssignmentClick)
        }

        composable(BottomNavItem.MyAssignments.screen_route) {
            MyAssignmentsScreen(mainViewModel, onAssignmentClick)
        }

        val onLogOut = {
            navController.navigate(Screens.LOGIN.name){
                popUpTo(BottomNavItem.Home.screen_route){
                    inclusive = true
                }
            }
        }
        composable(BottomNavItem.Profile.screen_route) {
            ProfileScreen(mainViewModel, onLogOut)
        }
        composable(Screens.ASSIGNMENT_DETAILS.name){
            AssignmentDetailScreen(mainViewModel)
        }
        composable(Screens.LIST_OF_PASSENGERS.name){
            PassengerListScreen(mainViewModel)
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController, isVisible: Boolean) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.MyAssignments,
        BottomNavItem.Profile
    )
    AnimatedVisibility(visible = isVisible){
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.white),
            contentColor = Color.Black
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(id = item.icon), contentDescription = stringResource(item.title) ) },
                    label = { Text(text = stringResource(item.title), fontSize = 9.sp) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.screen_route,
                    onClick = {
                        navController.navigate(item.screen_route) {

                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

