package id.angga.pokemonsprout.ui.pokemon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.angga.pokemonsprout.data.pokemon.PokemonRepository
import id.angga.pokemonsprout.model.Pokemon
import id.angga.pokemonsprout.model.Type
import kotlinx.coroutines.launch
import javax.inject.Inject
import id.angga.pokemonsprout.data.Result

data class PokemonListUiState(
    val pokemon: List<Pokemon> = listOf(),
    val loading: Boolean = false,
)

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    var uiState by mutableStateOf(PokemonListUiState(loading = true))
        private set
    private val pokemonList = mutableStateListOf<Pokemon>()

    var showFavorites by mutableStateOf(false)
    var typeFilter by mutableStateOf<Type?>(null)

    init {
        refresh()
    }

    /**
     * Refresh Pokemon list
     */
    fun refresh() {
        viewModelScope.launch {
            when (val result = pokemonRepository.getAllPokemon()) {
                is Result.Success -> {
                    pokemonList.addAll(result.data)
                    uiState = uiState.copy(
                        loading = false,
                        pokemon = pokemonList.toList()
                    )
                }

                is Result.Error -> {
                    throw result.exception
                }
            }
        }
    }

}