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
import mx.edu.utez.integradoraaplicacionesmoviles.ui.viewmodel.PlayerViewModel

@Composable
fun AppNavigation(
    playerViewModel: PlayerViewModel,
    songViewModel: SongViewModel,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    val navController = rememberNavController()
    
    androidx.compose.runtime.LaunchedEffect(Unit) {
        songViewModel.loadSongs()
    }
    
    androidx.compose.runtime.LaunchedEffect(songViewModel.songs.value) {
        if (songViewModel.songs.value.isNotEmpty()) {
            playerViewModel.setPlaylist(songViewModel.songs.value)
        }
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                playerViewModel = playerViewModel,
                onNavigateToSongs = { navController.navigate("songs") },
                onPlayPause = onPlayPause,
                onNext = onNext,
                onPrevious = onPrevious
            )
        }

        composable("songs") {
            SongListScreen(
                viewModel = songViewModel,
                playerViewModel = playerViewModel,
                onNavigateToForm = { songId ->
                    val route = if (songId != null) "songForm/$songId" else "songForm"
                    navController.navigate(route)
                },
                onSongSelected = { selectedSong ->
                    playerViewModel.setCurrentSong(selectedSong)
                    navController.navigate("home")
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
        ) { entry ->
            val id = entry.arguments?.getInt("songId").takeIf { it != -1 }

            SongFormScreen(
                viewModel = songViewModel,
                songId = id,
                onFinish = {
                    // sincronizar playlist con el reproductor
                    playerViewModel.setPlaylist(songViewModel.songs.value)
                    navController.popBackStack()
                }
            )
        }

        composable("songForm") {
            SongFormScreen(
                viewModel = songViewModel,
                songId = null,
                onFinish = {
                    playerViewModel.setPlaylist(songViewModel.songs.value)
                    navController.popBackStack()
                }
            )
        }
    }
}
