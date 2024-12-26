package id.angga.pokemonsprout.ui.pokemon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.angga.pokemonsprout.model.Pokemon
import id.angga.pokemonsprout.ui.common.Pokeball
import id.angga.pokemonsprout.ui.common.PokemonImage
import id.angga.pokemonsprout.ui.common.PokemonTypeLabels
import id.angga.pokemonsprout.ui.common.TypeLabelMetrics
import id.angga.pokemonsprout.ui.common.formatId
import id.angga.pokemonsprout.ui.theme.PokemonTypesTheme

@Composable
fun PokemonListCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    PokemonTypesTheme(types = pokemon.typeOfPokemon) {
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
            color = PokemonTypesTheme.colorScheme.surface,
            contentColor = PokemonTypesTheme.colorScheme.onSurface
        ) {
            Box(
                modifier
                    .height(124.dp)
                    .clickable { onPokemonSelected(pokemon) }
            ) {
                Column(
                    Modifier.padding(top = 24.dp, start = 12.dp)
                ) {
                    PokemonName(pokemon.name)
                    Spacer(Modifier.height(8.dp))
                    PokemonTypeLabels(
                        types = pokemon.typeOfPokemon,
                        metrics = TypeLabelMetrics.SMALL
                    )
                }
                Text(
                    text = formatId(pokemon.id),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .graphicsLayer { alpha = 0.5f }
                        .padding(top = 8.dp, end = 12.dp)
                        .align(Alignment.TopEnd)
                )
                Pokeball(
                    tint = Color.White,
                    modifier = Modifier
                        .requiredSize(88.dp)
                        .graphicsLayer { alpha = 0.25f }
                        .align(Alignment.BottomEnd)
                        .offset(x = 0.dp, y = 0.dp)
                )

                PokemonImage(
                    image = pokemon.image,
                    description = pokemon.name,
                    modifier = Modifier
                        .padding(bottom = 6.dp, end = 6.dp)
                        .size(80.dp)
                        .align(Alignment.BottomEnd)
                )

            }
        }
    }
}

@Composable
private fun PokemonName(name: String?) {
    Text(
        text = name ?: "",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )
}


