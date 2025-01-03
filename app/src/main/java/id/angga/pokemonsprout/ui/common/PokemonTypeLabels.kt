package id.angga.pokemonsprout.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.angga.pokemonsprout.R
import id.angga.pokemonsprout.model.Type
import id.angga.pokemonsprout.ui.theme.PokemonTypesTheme

data class TypeLabelMetrics(
    val cornerRadius: Dp,
    val fontSize: TextUnit,
    val fontWeight: FontWeight = FontWeight.Normal,
    val verticalPadding: Dp,
    val horizontalPadding: Dp,
    val elementSpacing: Dp
) {
    companion object {
        val SMALL = TypeLabelMetrics(
            cornerRadius = 24.dp,
            fontSize = 10.sp,
            verticalPadding = 0.dp,
            horizontalPadding = 10.dp,
            elementSpacing = 4.dp
        )
        val MEDIUM = TypeLabelMetrics(
            cornerRadius = 24.dp,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            verticalPadding = 0.dp,
            horizontalPadding = 12.dp,
            elementSpacing = 8.dp
        )
        val LARGE = TypeLabelMetrics(
            cornerRadius = 24.dp,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            verticalPadding = 4.dp,
            horizontalPadding = 16.dp,
            elementSpacing = 12.dp
        )
    }
}

@Composable
fun PokemonTypeLabels(
    modifier: Modifier = Modifier,
    types: List<String>,
    metrics: TypeLabelMetrics = TypeLabelMetrics.MEDIUM
) {
    PokemonTypesTheme(types = listOf(types[0])) {
        types.forEach {
            TypeLabel(modifier = modifier, text = it, metrics = metrics)
            Spacer(modifier = Modifier.size(metrics.elementSpacing))
        }
    }
}

@Composable
fun TypeLabel(
    modifier: Modifier = Modifier,
    text: String,
    colored: Boolean = false,
    metrics: TypeLabelMetrics = TypeLabelMetrics.MEDIUM
) {
    Surface(
        modifier = modifier,
        color = if (colored) PokemonTypesTheme.colorScheme.surface else Color(0x38FFFFFF),
        contentColor = PokemonTypesTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(metrics.cornerRadius)
    ) {
        Text(
            text = text,
            fontSize = metrics.fontSize,
            fontWeight = metrics.fontWeight,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                start = metrics.horizontalPadding,
                end = metrics.horizontalPadding,
                top = metrics.verticalPadding,
                bottom = metrics.verticalPadding
            ),
        )
    }
}

fun mapTypeToIcon(type: Type): Int {
    return when (type) {
        Type.Normal -> return R.drawable.ic_type_normal
        Type.Fire -> R.drawable.ic_type_fire
        Type.Water -> R.drawable.ic_type_water
        Type.Electric -> R.drawable.ic_type_electric
        Type.Grass -> R.drawable.ic_type_grass
        Type.Ice -> R.drawable.ic_type_ice
        Type.Fighting -> R.drawable.ic_type_fighting
        Type.Poison -> R.drawable.ic_type_poison
        Type.Ground -> R.drawable.ic_type_ground
        Type.Flying -> R.drawable.ic_type_flying
        Type.Psychic -> R.drawable.ic_type_psychic
        Type.Bug -> R.drawable.ic_type_bug
        Type.Rock -> R.drawable.ic_type_rock
        Type.Ghost -> R.drawable.ic_type_ghost
        Type.Dragon -> R.drawable.ic_type_dragon
        Type.Dark -> R.drawable.ic_type_dark
        Type.Steel -> R.drawable.ic_type_steel
        Type.Fairy -> R.drawable.ic_type_fairy
    }
}

