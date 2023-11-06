package kz.divtech.odyssey.drive.presentation.ui.screens.my_tasks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kz.divtech.odyssey.drive.R
import kz.divtech.odyssey.drive.presentation.ui.MainActivityViewModel
import kz.divtech.odyssey.drive.common.Variables.PaddingDp
import kz.divtech.odyssey.drive.presentation.theme.OdysseyDriveTheme
import kz.divtech.odyssey.drive.presentation.ui.screens.BottomNavItem

@Preview
@Composable
fun MyAssignmentsScreenPreview(){
    MyTasksScreen(mainViewModel = viewModel(), onTaskClicked = {}, snackBarHostState = SnackbarHostState())
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyTasksScreen(mainViewModel: MainActivityViewModel,
                  viewModel: MyTasksViewModel = hiltViewModel(),
                  onTaskClicked: (taskId: Int) -> Unit,
                  snackBarHostState: SnackbarHostState
) {

    val tabs = listOf(stringResource(R.string.active), stringResource(R.string.archive))
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size})

    OdysseyDriveTheme {
        val screenTitle = stringResource(BottomNavItem.MyAssignments.title)
        LaunchedEffect(Unit){
            mainViewModel.setTitle(screenTitle)
        }

        Column(modifier = Modifier
            .background(color = Color(0xFFEBEDF0))) {
            TabRow(selectedTabIndex = pagerState.currentPage,
                containerColor = Color.White){
                tabs.forEachIndexed{ index, title ->
                    Tab(text = {Text(title)},
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(index) }
                        },
                        selected = pagerState.currentPage == index,
                        unselectedContentColor = Color(0xFF243254)
                    )
                }
            }

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = PaddingDp, start = PaddingDp, end = PaddingDp, bottom = 0.dp),
                verticalAlignment = Alignment.Top,
                state = pagerState){ pageIndex ->
                when(pageIndex){
                    0 -> ActiveTasksScreen(viewModel, onTaskClicked, snackBarHostState = snackBarHostState)
                    1 -> ArchiveTasksScreen(viewModel, onTaskClicked,  snackBarHostState = snackBarHostState)
                }
            }
        }
    }
}



