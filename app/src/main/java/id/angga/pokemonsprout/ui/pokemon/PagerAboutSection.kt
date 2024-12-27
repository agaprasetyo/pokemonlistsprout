package id.angga.pokemonsprout.ui.pokemon

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import id.angga.pokemonsprout.data.pokemon.SamplePokemonData
import id.angga.pokemonsprout.data.pokemon.mapSampleAbilitiesToDetailsList
import id.angga.pokemonsprout.model.Pokemon
import id.angga.pokemonsprout.ui.theme.PokemonAnggaSproutTheme
import id.angga.pokemonsprout.ui.theme.PokemonTypesTheme

@Composable
fun PagerAboutSection(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    abilities: List<PokemonDetailsAbilities>
) {
    Text(
        text = "ANGGA initial pager about section"
    )

}


@PreviewLightDark
@Composable
fun AboutSectionPreview() {
    val pokemon = SamplePokemonData[0]

    PokemonAnggaSproutTheme {
        PokemonTypesTheme(
            types = pokemon.typeOfPokemon
        ) {
            Surface(Modifier.fillMaxWidth()) {
                PagerAboutSection(
                    pokemon = pokemon,
                    abilities = mapSampleAbilitiesToDetailsList().take(2)
                )
            }
        }
    }
}