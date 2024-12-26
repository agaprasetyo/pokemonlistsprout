package id.angga.pokemonsprout.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.angga.pokemonsprout.data.PokemonDatabase
import id.angga.pokemonsprout.data.abilities.AbilitiesDao

@InstallIn(SingletonComponent::class)
@Module
object AbilitiesDatabaseModule {

    @Provides
    fun provideAbilitiesDao(database: PokemonDatabase): AbilitiesDao {
        return database.abilitiesDao()
    }
}