package id.angga.pokemonsprout.data.abilities

import com.apollographql.apollo3.ApolloClient
import id.angga.pokemonsprout.data.Result
import id.angga.pokemonsprout.model.Ability

class NetworkAbilitiesRepository(abilitiesDao: AbilitiesDao, apolloClient: ApolloClient) :
    AbilitiesRepository {
    override suspend fun getAllAbilities(): Result<List<Ability>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAbilityById(id: Int): Result<Ability> {
        TODO("Not yet implemented")
    }

}
