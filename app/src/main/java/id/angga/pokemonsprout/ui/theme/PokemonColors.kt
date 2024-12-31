package id.angga.pokemonsprout.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class PokemonColors {
    companion object {
        val Bug = Color(0xffaabb22)
        val Dark = Color(0xff775544)
        val Dragon = Color(0xff7766EE)
        val Electric = Color(0xffF0C03E)
        val Fairy = Color(0xffee99ee)
        val Fighting = Color(0xffbb5544)
        val Fire = Color(0xffff4422)
        val Flying = Color(0xff8899ff)
        val Ghost = Color(0xff9F5BBA)
        val Grass = Color(0xff4FC1A6)
        val Ground = Color(0xff775544)
        val Ice = Color(0xff66ccff)
        val Normal = Color(0xffaaaa99)
        val Poison = Color(0xffaa5599)
        val Psychic = Color(0xffff5599)
        val Rock = Color(0xffBBAA66)
        val Water = Color(0xff429BED)
        val Steel = Color(0xffaaaabb)
    }
}

val LocalPokemonTypeColorScheme = staticCompositionLocalOf {
    PokemonTypeColorScheme(
        primary = Color.Magenta,
        surface = Color.Magenta,
        onSurface = Color.Magenta,
        surfaceVariant = Color.Magenta
    )
}

@Immutable
data class PokemonTypeColorScheme(
    val primary: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val secondary: Color = primary,
    val tertiary: Color = primary
)

val PhysicalColors = MoveCategoryColors(
    primaryDark = Color(0xffE3300E),
    primaryLight = PokemonColors.Fire,
    surfaceDark = Color(0xff561F14),
    surfaceLight = Color(0xffFFDAD3),
    onSurfaceDark = Color(0xffFFDAD3),
    onSurfaceLight = Color(0xff3A0A03)
)

val SpecialColors = MoveCategoryColors(
    primaryDark = Color(0xffC7BFFF),
    primaryLight = PokemonColors.Dragon,
    surfaceDark = Color(0xff2F295F),
    surfaceLight = Color(0xffE4DFFF),
    onSurfaceDark = Color(0xffE4DFFF),
    onSurfaceLight = Color(0xff1A1249)
)

val StatusColors = MoveCategoryColors(
    primaryDark = Color(0xffFFB691),
    primaryLight = PokemonColors.Dark,
    surfaceDark = Color(0xff542102),
    surfaceLight = Color(0xffFFDBCB),
    onSurfaceDark = Color(0xffFFDBCB),
    onSurfaceLight = Color(0xff341100)
)

@Immutable
data class MoveCategoryColors(
    val primaryDark: Color,
    val primaryLight: Color,
    val surfaceDark: Color,
    val surfaceLight: Color,
    val onSurfaceDark: Color = Color.Unspecified,
    val onSurfaceLight: Color = Color.Unspecified,
)
