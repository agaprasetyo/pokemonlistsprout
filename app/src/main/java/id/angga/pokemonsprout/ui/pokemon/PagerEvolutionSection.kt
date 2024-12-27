package id.angga.pokemonsprout.ui.pokemon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PagerEvolutionSection(
    modifier: Modifier = Modifier,
    evolutions: List<PokemonDetailsEvolutions> = listOf(),
) {
    Text(
        text = "ANGGA initial pager evolution section"
    )
}