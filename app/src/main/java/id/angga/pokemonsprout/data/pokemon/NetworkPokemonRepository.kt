package id.angga.pokemonsprout.data.pokemon

import com.apollographql.apollo3.ApolloClient
import id.angga.pokemonsprout.data.Result
import id.angga.pokemonsprout.model.Pokemon

class NetworkPokemonRepository(pokemonDao: PokemonDao, apolloClient: ApolloClient) :
    PokemonRepository {
    override suspend fun getAllPokemon(): Result<List<Pokemon>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPokemonById(id: Int): Result<Pokemon> {
        TODO("Not yet implemented")
    }

}
