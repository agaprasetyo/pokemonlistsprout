package id.angga.pokemonsprout.ui.pokemon

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import id.angga.pokemonsprout.data.abilities.AbilitiesRepository
import id.angga.pokemonsprout.data.items.ItemsRepository
import id.angga.pokemonsprout.data.moves.MovesRepository
import id.angga.pokemonsprout.data.pokemon.PokemonRepository
import id.angga.pokemonsprout.data.Result
import id.angga.pokemonsprout.di.ViewModelFactoryProvider
import id.angga.pokemonsprout.model.Ability
import id.angga.pokemonsprout.model.EvolutionTrigger
import id.angga.pokemonsprout.model.Item
import id.angga.pokemonsprout.model.Move
import id.angga.pokemonsprout.model.Pokemon
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

data class PokemonDetailsEvolutions(
    val pokemon: Pokemon,
    val trigger: EvolutionTrigger,
    val targetLevel: Int,
    val item: Item?
)

data class PokemonDetailsMoves(
    val move: Move,
    val targetLevel: Int
)

data class PokemonDetailsAbilities(
    val ability: Ability,
    val isHidden: Boolean,
)

class PokemonDetailsViewModel @AssistedInject constructor(
    private val pokemonRepository: PokemonRepository,
    private val movesRepository: MovesRepository,
    private val itemsRepository: ItemsRepository,
    private val abilitiesRepository: AbilitiesRepository,
    @Assisted private val pokemon: Pokemon
) : ViewModel() {
    var details by mutableStateOf(pokemon)
        private set

    var evolutions by mutableStateOf(listOf<PokemonDetailsEvolutions>())
        private set

    var moves by mutableStateOf(listOf<PokemonDetailsMoves>())
        private set

    var abilities by mutableStateOf(listOf<PokemonDetailsAbilities>())
        private set

    @AssistedFactory
    interface PokemonDetailsViewModelFactory {
        fun create(pokemon: Pokemon): PokemonDetailsViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: PokemonDetailsViewModelFactory,
            pokemon: Pokemon
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(pokemon) as T
            }
        }
    }

    init {
        refresh(pokemon)
    }

    fun refresh(incomingPokemon: Pokemon) {
        viewModelScope.launch {

            movesRepository.getAllMoves()
            details = incomingPokemon

            val ev = mutableListOf<PokemonDetailsEvolutions>()
            incomingPokemon.evolutionChain.map {
                launch {
                    when (val result = pokemonRepository.getPokemonById(it.id)) {
                        is Result.Success -> {
                            ev.add(
                                PokemonDetailsEvolutions(
                                    pokemon = result.data,
                                    targetLevel = it.targetLevel,
                                    trigger = it.trigger,
                                    item = when (val itemResult =
                                        itemsRepository.getItemById(it.itemId)) {
                                        is Result.Success ->
                                            itemResult.data

                                        is Result.Error -> {
                                            println(itemResult.exception)
                                            null
                                        }
                                    }
                                )
                            )
                        }

                        is Result.Error -> {
                            println(result.exception)
                        }
                    }
                }
            }.joinAll()
            evolutions = ev
                .sortedBy { it.pokemon.id }
                .toList()

            val mv = mutableListOf<PokemonDetailsMoves>()
            incomingPokemon.movesList.map {
                launch {
                    when (val result = movesRepository.getMoveById(it.id)) {
                        is Result.Success -> {
                            mv.add(PokemonDetailsMoves(result.data, it.targetLevel))
                        }

                        is Result.Error -> {
                            println(result.exception)
                        }
                    }
                }
            }.joinAll()
            moves = mv.sortedBy { it.targetLevel }

            val ab = mutableListOf<PokemonDetailsAbilities>()
            incomingPokemon.abilitiesList.map {
                launch {
                    when (val result = abilitiesRepository.getAbilityById(it.id)) {
                        is Result.Success -> {
                            ab.add(PokemonDetailsAbilities(result.data, it.isHidden))
                        }

                        is Result.Error -> {
                            // TODO: Abilities only queried from local database which currently limited to gen 1 moves
                            println(result.exception)
                        }
                    }
                }
            }.joinAll()
            abilities = ab.sortedBy { it.isHidden }
        }
    }
}

@Composable
fun pokemonDetailsViewModel(pokemon: Pokemon): PokemonDetailsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).pokemonDetailsViewModelFactory()

    return viewModel(factory = PokemonDetailsViewModel.provideFactory(factory, pokemon))
}