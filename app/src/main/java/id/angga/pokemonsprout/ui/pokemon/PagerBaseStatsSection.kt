package id.angga.pokemonsprout.ui.pokemon

import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import id.angga.pokemonsprout.R
import id.angga.pokemonsprout.data.pokemon.SamplePokemonData
import id.angga.pokemonsprout.model.Pokemon
import id.angga.pokemonsprout.ui.common.Label
import id.angga.pokemonsprout.ui.theme.PokemonAnggaSproutTheme
import id.angga.pokemonsprout.ui.theme.PokemonTypesTheme
import kotlinx.coroutines.delay

@Composable
fun PagerBaseStatsSection(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    val stats = listOf(
        Stat(R.string.pager_label_stat_HP, pokemon.hp),
        Stat(R.string.pager_label_stat_Attack, pokemon.attack),
        Stat(R.string.pager_label_stat_defence, pokemon.defense),
        Stat(R.string.pager_label_stat_sp_attack, pokemon.specialAttack),
        Stat(R.string.pager_label_stat_sp_def, pokemon.specialDefense),
        Stat(R.string.pager_label_stat_speed, pokemon.speed),
    )

    Column(
        modifier
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        stats.forEachIndexed { idx, stat ->
            val statValue = remember { Animatable(0f) }

            LaunchedEffect(stat) {
                delay(70L * idx)
                statValue.animateTo(
                    targetValue = stat.progress,
                    animationSpec = spring(
                        0.6f,
                        1000f
                    )
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Label(
                    text = stringResource(stat.label),
                    modifier = Modifier
                        .weight(1f)
                        .graphicsLayer { alpha = 0.7f }
                )
                Text(
                    "${stat.value}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .weight(0.6f)
                )

                val indicatorColor by animateColorAsState(
                    targetValue = PokemonTypesTheme.colorScheme.primary,
                    tween(durationMillis = 500),
                    label = "statsProgressIndicatorColor"
                )

                LinearProgressIndicator(
                    progress = { statValue.value },
                    color = indicatorColor,
                    drawStopIndicator = {},
                    modifier = Modifier
                        .clip(RoundedCornerShape(100))
                        .weight(2.5f)
                )
            }
        }
    }
}

data class Stat(
    @StringRes val label: Int,
    val value: Int?,
    val max: Int = 200
) {
    val progress: Float =
        1f * (value ?: 0) / max
}

@PreviewLightDark
@Composable
fun BaseStatsSectionPreview() {
    val pokemon = SamplePokemonData.first()

    PokemonAnggaSproutTheme {
        Surface(Modifier.fillMaxWidth()) {
            PokemonTypesTheme(
                types = pokemon.typeOfPokemon
            ) {
                PagerBaseStatsSection(pokemon = pokemon)
            }
        }
    }
}