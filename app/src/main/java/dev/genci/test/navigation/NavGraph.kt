package dev.genci.test.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.genci.test.ui.detail.DetailScreen
import dev.genci.test.ui.detail.DetailViewModel
import dev.genci.test.ui.home.HomeScreen
import dev.genci.test.ui.home.HomeViewModel

object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{cocktailId}"
    fun detail(id: String) = "detail/$id"
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            val vm: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = vm,
                onItemClick = { id -> navController.navigate(Routes.detail(id)) }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("cocktailId") { type = NavType.StringType })
        ) {
            val vm: DetailViewModel = hiltViewModel()
            DetailScreen(viewModel = vm,
                onBackClick = { navController.popBackStack() })
        }
    }
}