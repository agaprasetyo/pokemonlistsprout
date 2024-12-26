package id.angga.pokemonsprout.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.angga.pokemonsprout.data.PokemonDatabase
import id.angga.pokemonsprout.data.items.ItemsDao

@InstallIn(SingletonComponent::class)
@Module
object ItemsDatabaseModule {

    @Provides
    fun provideItemsDao(database: PokemonDatabase): ItemsDao {
        return database.itemsDao()
    }
}