package id.angga.pokemonsprout.ui.pokemon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import id.angga.pokemonsprout.data.pokemon.SamplePokemonData
import id.angga.pokemonsprout.model.Pokemon
import id.angga.pokemonsprout.model.Type
import id.angga.pokemonsprout.ui.common.Pokeball
import id.angga.pokemonsprout.ui.theme.PokemonAnggaSproutTheme

@Composable
fun PokemonListScreenRoute(
    viewModel: PokemonListViewModel,
    onPokemonSelected: (Pokemon) -> Unit,
    pastPokemonSelected: Int?
) {
    PokemonListScreen(
        loading = viewModel.uiState.loading,
        pokemon = viewModel.uiState.pokemon,
        favorites = viewModel.favorites,
        showFavorites = viewModel.showFavorites,
        typeFilter = viewModel.typeFilter,
        pastPokemonSelected = pastPokemonSelected,
        onPokemonSelected = onPokemonSelected,
        onMenuItemClick = {

        }
    )
}

enum class FilterMenuState {
    Hidden,
    Visible,
    Types
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    loading: Boolean,
    pokemon: List<Pokemon>,
    favorites: List<Pokemon>,
    showFavorites: Boolean = false,
    typeFilter: Type? = null,
    pastPokemonSelected: Int? = null,
    onPokemonSelected: (Pokemon) -> Unit = {},
    onMenuItemClick: (FilterMenuEvent) -> Unit = {}
) {
    val listState = rememberLazyGridState()
    var filterMenuState by remember { mutableStateOf(FilterMenuState.Hidden) }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    LaunchedEffect(pastPokemonSelected) {
        pastPokemonSelected?.let { pastId ->
            val visibleItems = listState.layoutInfo.visibleItemsInfo
            val visible = visibleItems.filter { it.key == pastId }

            if (visible.isEmpty()) {
                listState.scrollToItem(pastId, -100)
            }
        }
    }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Pokemon") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f)
                ),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        Box(
            Modifier.fillMaxSize()
        ) {
            Pokeball(
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                modifier = Modifier
                    .size(256.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 90.dp, y = (-72).dp)
            )
            PokemonList(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .fillMaxWidth(),
                listState = listState,
                loading = loading,
                pokemon = pokemon,
                favorites = favorites,
                showFavorites = showFavorites,
                typeFilter = typeFilter,
                onPokemonSelected = onPokemonSelected
            )
        }
    }
}

@Composable
private fun PokemonList(
    modifier: Modifier = Modifier,
    listState: LazyGridState,
    loading: Boolean = false,
    pokemon: List<Pokemon>,
    favorites: List<Pokemon>,
    showFavorites: Boolean = false,
    typeFilter: Type? = null,
    onPokemonSelected: (Pokemon) -> Unit = {},
) {
    val loaded = remember { MutableTransitionState(!loading) }
    val pokemonToShow = if (showFavorites) favorites else pokemon
    val bottomContentPadding =
        96.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    LazyVerticalGrid(
        modifier = modifier.testTag("PokedexLazyGrid"),
        columns = GridCells.Fixed(2),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            top = 12.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = bottomContentPadding
        ),
        content = {
            if (loading) {
                item(span = { GridItemSpan(2) }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(vertical = 24.dp)
                        )
                    }
                }
            } else {
                loaded.targetState = true

                if (pokemonToShow.isEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            Text(
                                text = "No Pokemon match the following:",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(Modifier.height(16.dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                if (showFavorites) {
                                    Text(
                                        text = "Favorites",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                                if (typeFilter != null) {
                                    Text(
                                        text = "Type: $typeFilter",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }
                } else {
                    itemsIndexed(items = pokemonToShow, key = { _, p -> p.id }) { idx, p ->
                        AnimatedVisibility(
                            visibleState = loaded,
                            enter = slideInVertically(
                                animationSpec = tween(
                                    durationMillis = 500,
                                    delayMillis = idx / 2 * 120
                                ),
                                initialOffsetY = { it / 2 }
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 400,
                                    delayMillis = idx / 2 * 150
                                ),
                            ),
                            exit = ExitTransition.None,
                            label = "pokemonCardTransition"
                        ) {
                            PokemonListCard(
                                pokemon = p,
                                onPokemonSelected = onPokemonSelected
                            )
                        }
                    }
                }
            }
        }
    )
}

sealed class FilterMenuEvent {
    data class ToggleFavorites(val filterFavorites: Boolean) : FilterMenuEvent()
    data class ShowTypes(val showTypes: Boolean) : FilterMenuEvent()
    data class FilterTypes(val typeToFilter: Type) : FilterMenuEvent()
}

@PreviewLightDark
@Composable
private fun PokedexScreenPreview() {
    var pokemon by remember { mutableStateOf(SamplePokemonData) }
    val favorites by remember { mutableStateOf(SamplePokemonData.take(5)) }
    var showFavorites by remember { mutableStateOf(false) }
    var typeFilter by remember { mutableStateOf<Type?>(null) }

    PokemonAnggaSproutTheme {
        PokemonListScreen(
            loading = false,
            pokemon = pokemon,
            favorites = favorites,
            showFavorites = showFavorites,
            onMenuItemClick = { result ->
                when (result) {
                    is FilterMenuEvent.ToggleFavorites -> {
                        showFavorites = !showFavorites
                        pokemon = if (showFavorites) {
                            favorites.toList()
                        } else {
                            SamplePokemonData.toList()
                        }
                    }

                    is FilterMenuEvent.FilterTypes -> {
                        typeFilter =
                            if (typeFilter != result.typeToFilter) result.typeToFilter else null

                        pokemon = if (typeFilter != null) {
                            SamplePokemonData.filter { it.typeOfPokemon.contains(typeFilter.toString()) }
                        } else {
                            SamplePokemonData.toList()
                        }
                    }

                    is FilterMenuEvent.ShowTypes -> {}
                }
            }
        )
    }
}