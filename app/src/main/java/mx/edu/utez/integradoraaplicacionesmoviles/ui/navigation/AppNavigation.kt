package mx.edu.utez.integradoraaplicacionesmoviles.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.edu.utez.integradoraaplicacionesmoviles.ui.screens.home.HomeScreen
import mx.edu.utez.integradoraaplicacionesmoviles.ui.screens.songform.SongFormScreen
import mx.edu.utez.integradoraaplicacionesmoviles.ui.screens.songs.SongListScreen
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.SongViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val songViewModel = SongViewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                onNavigateToSongs = {
                    navController.navigate("songs")
                }
            )
        }

        composable("songs") {
            SongListScreen(
                viewModel = songViewModel,
                onNavigateToForm = { songId ->
                    val route = if (songId != null) {
                        "songForm/$songId"
                    } else {
                        "songForm"
                    }
                    navController.navigate(route)
                }
            )
        }

        composable(
            route = "songForm/{songId}",
            arguments = listOf(
                navArgument("songId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("songId").takeIf { it != -1 }

            SongFormScreen(
                viewModel = songViewModel,
                songId = id,
                onFinish = {
                    navController.popBackStack()
                }
            )
        }

        composable("songForm") {
            SongFormScreen(
                viewModel = songViewModel,
                songId = null,
                onFinish = {
                    navController.popBackStack()
                }
            )
        }
    }
}