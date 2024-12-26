package id.angga.pokemonsprout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.angga.pokemonsprout.data.abilities.AbilitiesRepository
import id.angga.pokemonsprout.data.items.ItemsRepository
import id.angga.pokemonsprout.data.moves.MovesRepository
import id.angga.pokemonsprout.data.pokemon.PokemonRepository
import kotlinx.coroutines.launch
import id.angga.pokemonsprout.data.Result
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val movesRepository: MovesRepository,
    private val itemsRepository: ItemsRepository,
    private val abilitiesRepository: AbilitiesRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            println("Populating databases...")

            when (val pokemonResults = pokemonRepository.getAllPokemon()) {
                is Result.Success -> {
                    println("Pokemon database: ${pokemonResults.data.size}")
                }

                is Result.Error -> {
                    throw pokemonResults.exception
                }
            }

            when (val movesResults = movesRepository.getAllMoves()) {
                is Result.Success -> {
                    println("Moves database: ${movesResults.data.size}")
                }

                is Result.Error -> {
                    throw movesResults.exception
                }
            }

            when (val itemsResults = itemsRepository.getAllItems()) {
                is Result.Success -> {
                    println("Items database: ${itemsResults.data.size}")
                }

                is Result.Error -> {
                    throw itemsResults.exception
                }
            }

            when (val abilitiesResults = abilitiesRepository.getAllAbilities()) {
                is Result.Success -> {
                    println("Abilities database: ${abilitiesResults.data.size}")
                }

                is Result.Error -> {
                    throw abilitiesResults.exception
                }
            }
        }
    }
}