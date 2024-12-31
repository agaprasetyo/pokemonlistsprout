package id.angga.pokemonsprout.ui.pokemon

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import id.angga.pokemonsprout.R
import id.angga.pokemonsprout.model.Move
import id.angga.pokemonsprout.model.MoveCategory
import id.angga.pokemonsprout.ui.common.TypeLabel
import id.angga.pokemonsprout.ui.common.TypeLabelMetrics
import id.angga.pokemonsprout.ui.theme.MoveCategoryTheme
import id.angga.pokemonsprout.ui.theme.PokemonTypesTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerMovesSection(
    modifier: Modifier = Modifier,
    moves: List<PokemonDetailsMoves>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = 24.dp,
            start = 24.dp,
            end = 24.dp,
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 60.dp
        )
    ) {
        stickyHeader {
            val textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)

            CompositionLocalProvider(
                LocalTextStyle provides textStyle,
                LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.pager_label_moves_level),
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .requiredWidth(40.dp)
                            .padding(end = 12.dp)
                    )
                    Text(
                        text = stringResource(R.string.pager_label_moves_name),
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = stringResource(R.string.pager_label_moves_type),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(75.dp)
                    )
                    Text(
                        text = stringResource(R.string.pager_label_moves_category),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(48.dp)
                    )
                    Text(
                        text = stringResource(R.string.pager_label_moves_power),
                        textAlign = TextAlign.End,
                        modifier = Modifier.requiredWidth(40.dp)
                    )
                    Text(
                        text = stringResource(R.string.pager_label_moves_accuracy),
                        textAlign = TextAlign.End,
                        modifier = Modifier.requiredWidth(40.dp)
                    )
                }
            }
        }
        items(moves) { (move, targetLevel) ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$targetLevel",
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .requiredWidth(40.dp)
                        .padding(end = 12.dp)
                )
                Text(
                    move.name.split("-").joinToString(" ") {
                        it[0].uppercase() + it.substring(1)
                    },
                    Modifier.weight(1f)
                )
                PokemonTypesTheme(types = listOf(move.type)) {
                    TypeLabel(
                        modifier = Modifier.requiredWidth(75.dp),
                        text = move.type,
                        colored = true,
                        metrics = TypeLabelMetrics.MEDIUM
                    )
                }
                Box(
                    Modifier.requiredWidth(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CategoryIcon(
                        modifier = Modifier.size(24.dp),
                        move = move
                    )
                }
                Text(
                    "${move.power ?: "—"}",
                    textAlign = TextAlign.End,
                    modifier = Modifier.requiredWidth(40.dp)
                )
                Text(
                    text = "${move.accuracy ?: "—"}",
                    textAlign = TextAlign.End,
                    modifier = Modifier.requiredWidth(40.dp)
                )
            }
        }
    }
}

@Composable
fun CategoryIcon(
    modifier: Modifier = Modifier,
    move: Move
) {
    MoveCategoryTheme(
        category = MoveCategory.valueOf(move.category)
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = move.categoryIcon()),
                contentDescription = move.category,
                tint = MoveCategoryTheme.colorScheme.primary,
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        rotationX = 40f
                        rotationY = -15f
                    })
        }
    }
}