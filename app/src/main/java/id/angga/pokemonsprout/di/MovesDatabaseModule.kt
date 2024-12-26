package id.angga.pokemonsprout.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.angga.pokemonsprout.data.PokemonDatabase
import id.angga.pokemonsprout.data.moves.MovesDao

@InstallIn(SingletonComponent::class)
@Module
object MovesDatabaseModule {

    @Provides
    fun provideMovesDao(database: PokemonDatabase): MovesDao {
        return database.movesDao()
    }
}