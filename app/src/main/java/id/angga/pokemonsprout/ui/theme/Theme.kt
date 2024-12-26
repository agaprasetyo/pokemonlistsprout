package id.angga.pokemonsprout.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.materialkolor.PaletteStyle
import com.materialkolor.ktx.contrastRatio
import com.materialkolor.ktx.darken
import com.materialkolor.ktx.lighten
import com.materialkolor.rememberDynamicColorScheme
import id.angga.pokemonsprout.model.Type

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun PokemonAnggaSproutTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun PokemonTypesTheme(
    types: List<String>,
    paletteStyle: PaletteStyle = PaletteStyle.TonalSpot,
    content: @Composable () -> Unit
) {
    val seedColor = mapTypeToSeedColor(types = types)

    val kolorScheme = getDynamicColorScheme(
        seedColor = seedColor,
        paletteStyle = paletteStyle
    )

    val extendedTypesColors = mapDynamicPokemonColorScheme(
        seedColor = seedColor,
        colorScheme = kolorScheme,
    )

    CompositionLocalProvider(
        LocalPokemonTypeColorScheme provides extendedTypesColors,
        LocalContentColor provides extendedTypesColors.onSurface
    ) {
        MaterialTheme(
            colorScheme = kolorScheme
        ) {
            content()
        }
    }
}

fun mapTypeToSeedColor(
    types: List<String>,
): Color {
    val firstType = types[0]

    return when (Type.valueOf(firstType)) {
        Type.Bug -> PokemonColors.Bug
        Type.Dark -> PokemonColors.Dark
        Type.Dragon -> PokemonColors.Dragon
        Type.Electric -> PokemonColors.Electric
        Type.Fairy -> PokemonColors.Fairy
        Type.Fighting -> PokemonColors.Fighting
        Type.Fire -> PokemonColors.Fire
        Type.Flying -> PokemonColors.Flying
        Type.Ghost -> PokemonColors.Ghost
        Type.Grass -> PokemonColors.Grass
        Type.Ground -> PokemonColors.Ground
        Type.Ice -> PokemonColors.Ice
        Type.Normal -> PokemonColors.Normal
        Type.Poison -> PokemonColors.Poison
        Type.Psychic -> PokemonColors.Psychic
        Type.Rock -> PokemonColors.Rock
        Type.Steel -> PokemonColors.Steel
        Type.Water -> PokemonColors.Water
    }
}

object PokemonTypesTheme {
    val colorScheme: PokemonTypeColorScheme
        @Composable
        get() = LocalPokemonTypeColorScheme.current
}

@Composable
fun getDynamicColorScheme(
    seedColor: Color,
    paletteStyle: PaletteStyle,
    isDark: Boolean = isSystemInDarkTheme()
) = rememberDynamicColorScheme(
    seedColor = seedColor,
    isDark = isDark,
    style = paletteStyle
)

@Composable
fun mapDynamicPokemonColorScheme(
    seedColor: Color,
    colorScheme: ColorScheme,
    useDarkTheme: Boolean = isSystemInDarkTheme()
): PokemonTypeColorScheme {
    return if (useDarkTheme) {
        PokemonTypeColorScheme(
            primary = colorScheme.primaryContainer.darken(0.4f),
            surface = colorScheme.primaryContainer,
            onSurface = colorScheme.onSurface,
            surfaceVariant = colorScheme.onPrimary,
            secondary = colorScheme.secondary,
            tertiary = colorScheme.secondary,
        )
    } else {
        PokemonTypeColorScheme(
            primary = seedColor.lighten(0.7f),
            surface = seedColor,
            onSurface = if (seedColor.contrastRatio(colorScheme.onSecondary) > 2.2) {
                colorScheme.onSecondary
            } else {
                colorScheme.onSecondaryContainer
            },
            surfaceVariant = seedColor.lighten(0.7f),
            secondary = colorScheme.primary,
            tertiary = colorScheme.secondary,
        )
    }
}