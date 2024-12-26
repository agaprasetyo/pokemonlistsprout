package id.angga.pokemonsprout.ui.common

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import id.angga.pokemonsprout.R

@Composable
fun Pokeball(
    modifier: Modifier = Modifier,
    tint: Color = Color.Black,
) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = R.drawable.ic_catching_pokemon),
        contentDescription = "PokeBall",
        tint = tint,
    )
}