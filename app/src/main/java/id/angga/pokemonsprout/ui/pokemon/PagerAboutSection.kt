package id.angga.pokemonsprout.ui.pokemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.angga.pokemonsprout.R
import id.angga.pokemonsprout.data.pokemon.SamplePokemonData
import id.angga.pokemonsprout.data.pokemon.mapSampleAbilitiesToDetailsList
import id.angga.pokemonsprout.model.Pokemon
import id.angga.pokemonsprout.ui.common.Label
import id.angga.pokemonsprout.ui.theme.PokemonAnggaSproutTheme
import id.angga.pokemonsprout.ui.theme.PokemonTypesTheme

@Composable
fun PagerAboutSection(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    abilities: List<PokemonDetailsAbilities>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(28.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = pokemon.description,
            lineHeight = 24.sp
        )
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 3.dp
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(Modifier.weight(1f)) {
                    Label(text = stringResource(R.string.pager_label_about_height))
                    Spacer(Modifier.height(12.dp))
                    Text("${pokemon.height}m")
                }
                Column(Modifier.weight(1f)) {
                    Label(text = stringResource(R.string.pager_label_about_weight))
                    Spacer(Modifier.height(12.dp))
                    Text("${pokemon.weight}kg")
                }
            }
        }
        if (abilities.isNotEmpty()) {
            AbilitiesDetails(abilities = abilities)
        }
        BreedingDetails(pokemon = pokemon)
        Spacer(Modifier.height(128.dp))
    }

}

@Composable
private fun AbilitiesDetails(
    modifier: Modifier = Modifier,
    abilities: List<PokemonDetailsAbilities>
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.pager_label_about_abilities),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            abilities.forEach {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    tonalElevation = 3.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        if (it.isHidden) {
                            Text(
                                text = stringResource(R.string.pager_label_about_hidden).uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                        Text(text = it.ability.name)
                        Spacer(Modifier.height(2.dp))
                        Label(text = it.ability.description)
                    }
                }
            }
        }
    }
}

@Composable
private fun BreedingDetails(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.pager_label_about_breeding),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(24.dp))
        Row(Modifier.fillMaxWidth()) {
            Label(
                text = stringResource(R.string.pager_label_about_gender),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            )
            if (pokemon.genderRate != -1) {
                Row(Modifier.weight(2f)) {
                    val femaleGenderRate = pokemon.genderRate * 12.5
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_male_20),
                            contentDescription = null,
                            tint = Color(0xff6C79DB)
                        )
                        Spacer(Modifier.width(2.dp))
                        Text("${100 - femaleGenderRate}%")
                    }
                    Spacer(Modifier.width(12.dp))
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_female_20),
                            contentDescription = null,
                            tint = Color(0xffF0729F)
                        )
                        Spacer(Modifier.width(2.dp))
                        Text("$femaleGenderRate%")
                    }
                }
            } else {
                Text(
                    text = stringResource(R.string.pager_label_about_genderless),
                    modifier = Modifier.weight(2.2f)
                )
            }
        }
        Spacer(Modifier.height(18.dp))
        Row(Modifier.fillMaxWidth()) {
            Label(
                text = stringResource(R.string.pager_label_about_egg_group),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            )
            Text(
                "-",
                modifier = Modifier.weight(2f)
            )
        }
        Spacer(Modifier.height(18.dp))
        Row(Modifier.fillMaxWidth()) {
            Label(
                text = stringResource(R.string.pager_label_about_egg_cycles),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            )
            Text(
                "-",
                modifier = Modifier.weight(2f)
            )
        }
    }
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