package id.angga.pokemonsprout

import android.app.Application
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.HiltAndroidApp
import id.angga.pokemonsprout.data.pokemon.SamplePokemonData
import id.angga.pokemonsprout.ui.pokemon.PokemonListScreenRoute
import id.angga.pokemonsprout.ui.theme.Material3Transitions

@HiltAndroidApp
class PokemonApplication : Application()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PokemonApp() {
    val navController = rememberNavController()
    val density = LocalDensity.current
    var pokemon by remember { mutableStateOf(SamplePokemonData.first()) }

    NavHost(
        navController = navController,
        startDestination = "list",
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
        enterTransition = { Material3Transitions.SharedXAxisEnterTransition(density) },
        popEnterTransition = { Material3Transitions.SharedXAxisPopEnterTransition(density) },
        exitTransition = { Material3Transitions.SharedXAxisExitTransition(density) },
        popExitTransition = { Material3Transitions.SharedXAxisPopExitTransition(density) }
    ) {
        composable(
            route = "list",
            popEnterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            val pastPokemonId = it.savedStateHandle.get<Int>("pokemonId")

            PokemonListScreenRoute(
                viewModel = hiltViewModel(),
                onPokemonSelected = {
                    pokemon = it
                    navController.navigate("details")
                },
                pastPokemonSelected = pastPokemonId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }


}