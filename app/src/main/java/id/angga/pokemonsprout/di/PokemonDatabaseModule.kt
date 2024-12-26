package id.angga.pokemonsprout.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.angga.pokemonsprout.data.PokemonDatabase
import id.angga.pokemonsprout.data.pokemon.PokemonDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PokemonDatabaseModule {

    @Provides
    fun providePokemonDao(database: PokemonDatabase): PokemonDao {
        return database.pokemonDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): PokemonDatabase {
        return Room
            .databaseBuilder(
                appContext,
                PokemonDatabase::class.java,
                "anggapokemon.db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }
}