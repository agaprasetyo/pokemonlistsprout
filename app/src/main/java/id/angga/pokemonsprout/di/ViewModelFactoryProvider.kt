package id.angga.pokemonsprout.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import id.angga.pokemonsprout.ui.pokemon.PokemonDetailsViewModel

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun pokemonDetailsViewModelFactory(): PokemonDetailsViewModel.PokemonDetailsViewModelFactory
}
