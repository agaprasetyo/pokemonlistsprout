package id.angga.pokemonsprout

import android.app.Application
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import id.angga.pokemonsprout.ui.theme.Material3Transitions

@HiltAndroidApp
class PokemonApplication : Application()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PokemonApp() {
    val navController = rememberNavController()
    val density = LocalDensity.current

    Greeting("ANGGA POKEMON")


}