package dev.me.mysmov.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.me.mysmov.feature.HomeContainer
import dev.me.mysmov.feature.detail.MovieDetailScreen

object AppRoutes {
    const val HOME_CONTAINER = "home_container"
    const val MOVIE_DETAIL = "detail/{movieId}"

    fun movieDetail(movieId: Int) = "detail/$movieId"
}

@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME_CONTAINER
    ) {
        composable(AppRoutes.HOME_CONTAINER) {
            HomeContainer(
                onNavigateToDetail = { movieId ->
                    navController.navigate(AppRoutes.movieDetail(movieId))
                }
            )
        }
        composable(AppRoutes.MOVIE_DETAIL) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 0
            MovieDetailScreen(
                id = movieId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
