package id.angga.pokemonsprout.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.angga.pokemonsprout.data.items.ItemsDao
import id.angga.pokemonsprout.data.items.ItemsRepository
import id.angga.pokemonsprout.data.items.NetworkItemsRepository

@InstallIn(SingletonComponent::class)
@Module
object NetworkItemsRepositoryModule {

    @Provides
    fun provideItemsRepository(
        itemsDao: ItemsDao,
        apolloClient: ApolloClient
    ): ItemsRepository {
        return NetworkItemsRepository(itemsDao, apolloClient)
    }
}