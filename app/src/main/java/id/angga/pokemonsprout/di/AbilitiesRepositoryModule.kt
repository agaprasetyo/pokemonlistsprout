package id.angga.pokemonsprout.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.angga.pokemonsprout.data.abilities.AbilitiesDao
import id.angga.pokemonsprout.data.abilities.AbilitiesRepository
import id.angga.pokemonsprout.data.abilities.NetworkAbilitiesRepository

@InstallIn(SingletonComponent::class)
@Module
object NetworkAbilitiesRepositoryModule {

    @Provides
    fun provideAbilitiesRepository(
        abilitiesDao: AbilitiesDao,
        apolloClient: ApolloClient
    ): AbilitiesRepository {
        return NetworkAbilitiesRepository(abilitiesDao, apolloClient)
    }
}