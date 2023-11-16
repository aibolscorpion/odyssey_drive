package kz.divtech.odyssey.drive.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.common.Constants.LOADING
import kz.divtech.odyssey.drive.presentation.ui.screens.BottomNavItem
import kz.divtech.odyssey.drive.presentation.ui.screens.Screens
import kz.divtech.odyssey.drive.presentation.ui.screens.main.MainScreen
import kz.divtech.odyssey.drive.presentation.ui.screens.my_tasks.MyTasksScreen
import kz.divtech.odyssey.drive.presentation.ui.screens.profile.ProfileScreen
import kz.divtech.odyssey.drive.presentation.ui.screens.login.LoginScreen
import kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.AssignmentDetailScreen
import kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.CompletedScreen
import kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.passengers.PassengerListScreen
import kz.divtech.odyssey.drive.presentation.theme.ColorPrimary
import kz.divtech.odyssey.drive.presentation.theme.GreyAlpha50
import kz.divtech.odyssey.drive.presentation.ui.screens.loading.LoadingScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            MainScreenView()
        }
    }
}

@Preview
@Composable
fun MainScreenView(mainViewModel: MainActivityViewModel = hiltViewModel()){
    val title: String by mainViewModel.title.observeAsState(stringResource(R.string.app_name))
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }

    val bottomBarState: Boolean
    val topBarState: Boolean
    val canNavigateBack: Boolean

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when(navBackStackEntry?.destination?.route){
        null, Screens.LOGIN.name -> {
            bottomBarState = false
            topBarState = false
            canNavigateBack = false
        }
        BottomNavItem.Main.screen_route,
        BottomNavItem.MyAssignments.screen_route,
        BottomNavItem.Profile.screen_route -> {
            bottomBarState = true
            topBarState = true
            canNavigateBack = false
        }
        Screens.ASSIGNMENT_COMPLETED.name -> {
            bottomBarState = false
            topBarState = true
            canNavigateBack = false
        }
        else -> {
            bottomBarState = false
            topBarState = true
            canNavigateBack = true
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState,
            snackbar = { data ->
                Snackbar(snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.error)
            })
        },
        containerColor = Color.White,
        topBar = {
            Column {
                TopAppBar(
                    isVisible = topBarState,
                    title = title,
                    canNavigateBack = canNavigateBack,
                    navigateUp = {
                        navController.navigateUp()
                    })
                HorizontalDivider(thickness = 2.dp, color = Color(0xFFEBEDF0))
            }
        },
        bottomBar = {
            BottomNavigation(navController = navController, isVisible = bottomBarState)
        }
    ){ padding ->
        Box(modifier = Modifier.padding(padding)){
            NavigationGraph(mainViewModel = mainViewModel, navController = navController, snackBarHostState)
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
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF243254)
                    )
                )
            },
            navigationIcon = {
                if(canNavigateBack){
                    IconButton(onClick = navigateUp ){
                        Icon(painter = painterResource(R.drawable.icon_arraw_back),
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
fun NavigationGraph(mainViewModel: MainActivityViewModel, navController: NavHostController, snackBarHostState: SnackbarHostState) {

    NavHost(navController, startDestination = getStartDestination(mainViewModel)) {
        composable(Screens.LOADING.name) {
            LoadingScreen()
        }

        composable(Screens.LOGIN.name) {
            LoginScreen(snackBarHostState = snackBarHostState)
        }

        val onTaskClick : (taskId: Int) -> Unit = { taskId ->
            navController.navigate("${Screens.ASSIGNMENT_DETAILS.name}/${taskId}")
        }

        composable(BottomNavItem.Main.screen_route) {
            MainScreen(mainViewModel, onTaskClick = onTaskClick, snackBarHostState = snackBarHostState)
        }

        composable(BottomNavItem.MyAssignments.screen_route) {
            MyTasksScreen(mainViewModel, onTaskClicked = onTaskClick, snackBarHostState = snackBarHostState)
        }

        composable(BottomNavItem.Profile.screen_route) {
            ProfileScreen(mainViewModel = mainViewModel, snackbarHostState = snackBarHostState)
        }

        composable("${Screens.ASSIGNMENT_DETAILS.name}/{taskId}",
            arguments = listOf(
                navArgument("taskId") {
                    defaultValue = 0
                    type = NavType.IntType
                }
            )
        ){ navBackStackEntry ->
            val taskId = navBackStackEntry.arguments?.getInt("taskId")
            taskId?.let {
                AssignmentDetailScreen(
                    mainViewModel,
                    onPassengerListClicked = { seatCount ->
                        navController.navigate("${Screens.LIST_OF_PASSENGERS.name}/${taskId}/${seatCount}")
                     },
                    onCompleteClicked = { descText, taskTime ->
                        navController.navigate("ASSIGNMENT_COMPLETED/${descText}/${taskTime}"){
                            launchSingleTop = true
                        }
                    },
                    taskId = taskId,
                    snackbarHostState = snackBarHostState
                )
            }
        }

        composable("${Screens.LIST_OF_PASSENGERS.name}/{taskId}/{seatCount}",
            arguments = listOf(
                navArgument("taskId"){type = NavType.IntType },
                navArgument("seatCount"){type = NavType.IntType }
            )
        ){ navBackStackEntry ->
            val taskId = navBackStackEntry.arguments?.getInt("taskId") ?: 0
            val seatCount = navBackStackEntry.arguments?.getInt("seatCount") ?: 0
            PassengerListScreen(mainViewModel, taskId, seatCount, snackBarHostState = snackBarHostState)
        }

        composable("${Screens.ASSIGNMENT_COMPLETED.name}/{descText}/{taskTime}",
            arguments = listOf(
                navArgument("descText"){type = NavType.StringType},
                navArgument("taskTime"){type = NavType.StringType},
            )){ navBackStackEntry ->
            val descText = navBackStackEntry.arguments?.getString("descText") ?: ""
            val taskTime = navBackStackEntry.arguments?.getString("taskTime") ?: "00:00 - 00:00"
            CompletedScreen(
                descText,
                taskTime,
                onAssignmentListClicked = {
                    navController.navigate(BottomNavItem.MyAssignments.screen_route){
                        popUpTo(Screens.ASSIGNMENT_DETAILS.name)
                    }
                })
        }
    }
}
@Composable
fun BottomNavigation(navController: NavController, isVisible: Boolean) {
    val items = listOf(
        BottomNavItem.Main,
        BottomNavItem.MyAssignments,
        BottomNavItem.Profile
    )
    AnimatedVisibility(visible = isVisible){
        Column {
            HorizontalDivider()
            NavigationBar(
                containerColor = colorResource(id = R.color.white),
                contentColor = Color.Black
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(painterResource(id = item.icon),
                            contentDescription = stringResource(item.title))},
                        label = { Text(text = stringResource(item.title), fontSize = 9.sp) },
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
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ColorPrimary,
                            selectedTextColor = ColorPrimary,
                            unselectedIconColor = GreyAlpha50,
                            unselectedTextColor = GreyAlpha50,
                            indicatorColor = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun getStartDestination(mainViewModel: MainActivityViewModel): String{
    val dataStoreManager = mainViewModel.dataStoreManager
    return when(dataStoreManager.getTokenValue()){
            "" -> Screens.LOGIN.name
            LOADING -> Screens.LOADING.name
            else -> BottomNavItem.Main.screen_route
        }
}


