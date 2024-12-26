package id.angga.pokemonsprout.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.angga.pokemonsprout.data.pokemon.NetworkPokemonRepository
import id.angga.pokemonsprout.data.pokemon.PokemonDao
import id.angga.pokemonsprout.data.pokemon.PokemonRepository

@InstallIn(SingletonComponent::class)
@Module
object NetworkPokemonRepositoryModule {

    @Provides
    fun providePokemonRepository(
        pokemonDao: PokemonDao,
        apolloClient: ApolloClient
    ): PokemonRepository {
        return NetworkPokemonRepository(pokemonDao, apolloClient)
    }
}