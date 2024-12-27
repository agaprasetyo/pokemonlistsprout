package id.angga.pokemonsprout.ui.pokemon

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import id.angga.pokemonsprout.R
import id.angga.pokemonsprout.data.pokemon.SamplePokemonData
import id.angga.pokemonsprout.data.pokemon.mapSampleEvolutionsToList
import id.angga.pokemonsprout.model.EvolutionTrigger
import id.angga.pokemonsprout.model.Item
import id.angga.pokemonsprout.model.Pokemon
import id.angga.pokemonsprout.ui.common.Pokeball
import id.angga.pokemonsprout.ui.common.PokemonImage
import id.angga.pokemonsprout.ui.common.itemAssetsUri
import id.angga.pokemonsprout.ui.theme.PokemonAnggaSproutTheme

@Composable
fun PagerEvolutionSection(
    modifier: Modifier = Modifier,
    evolutions: List<PokemonDetailsEvolutions> = listOf(),
) {
    Column(modifier.padding(24.dp)) {
        if (evolutions.size > 1) {
            Text(
                text = stringResource(R.string.pager_label_evolution_chain),
                style = MaterialTheme.typography.titleMedium,
            )

            evolutions.forEachIndexed { idx, evo ->
                if (idx < evolutions.size - 1) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val e1 = evo
                        val e2 = evolutions[idx + 1]

                        EvolutionCard(e1.pokemon)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.surfaceTint
                            )
                            Spacer(Modifier.height(4.dp))

                            e2.item?.let {
                                ItemImage(
                                    item = e2.item,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = e2.item.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )
                            } ?: run {
                                Text(
                                    text = when (e2.trigger) {
                                        EvolutionTrigger.Trade ->
                                            "Trade"

                                        else ->
                                            "Lvl ${e2.targetLevel}"
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        EvolutionCard(e2.pokemon)
                    }
                }
                if (idx < evolutions.size - 2)
                    HorizontalDivider()
            }
        } else {
            Text(text = stringResource(R.string.pager_label_evolution_chain_empty))
        }
    }
}

@Composable
private fun EvolutionCard(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .width(128.dp)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Pokeball(
                tint = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.size(80.dp),
            )
            PokemonImage(
                image = pokemon.id,
                modifier = Modifier.size(72.dp)
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(pokemon.name)
        Spacer(Modifier.height(4.dp))
    }
}

@Composable
fun ItemImage(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Inside,
    item: Item
) {
    AsyncImage(
        model = itemAssetsUri(item.sprite),
        placeholder = painterResource(id = R.drawable.item_flame_orb),
        imageLoader = LocalContext.current.imageLoader,
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun EvolutionsSectionPreview() {
    PokemonAnggaSproutTheme {
        Surface {
            Column {
                PagerEvolutionSection(
                    modifier = Modifier.padding(vertical = 32.dp),
                    evolutions = mapSampleEvolutionsToList(
                        SamplePokemonData.first { it.name == "Pikachu" }.evolutionChain
                    )
                )
                PagerEvolutionSection(
                    modifier = Modifier.padding(vertical = 32.dp),
                    evolutions = mapSampleEvolutionsToList(
                        SamplePokemonData[3].evolutionChain
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun EvolutionCardPreview() {
    PokemonAnggaSproutTheme {
        Surface {
            Column {
                EvolutionCard(pokemon = SamplePokemonData[1])
                EvolutionCard(pokemon = SamplePokemonData[4])
            }
        }
    }
}