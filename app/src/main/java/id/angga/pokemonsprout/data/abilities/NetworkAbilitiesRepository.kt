package id.angga.pokemonsprout.data.abilities

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import id.angga.pokemonsprout.AbilitiesQuery
import id.angga.pokemonsprout.data.Result
import id.angga.pokemonsprout.data.cleanupDescriptionText
import id.angga.pokemonsprout.model.Ability
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkAbilitiesRepository @Inject constructor(
    private val abilitiesDao: AbilitiesDao,
    private val apolloClient: ApolloClient
) :
    AbilitiesRepository {
    override suspend fun getAllAbilities(): Result<List<Ability>> {
        val localItems = abilitiesDao.getAll()

        if (localItems.isNotEmpty()) {
            delay(300)
            println("abilities from cache")
            return Result.Success(localItems)
        } else {
            return withContext(Dispatchers.IO) {
                println("abilities from network")
                val response = apolloClient.query(AbilitiesQuery()).execute()

                if (!response.hasErrors()) {
                    val abilitiesFromServer = response.data!!.abilities.map { model ->
                        Ability(
                            id = model.id,
                            name = model.name.split("-").joinToString(" ") { part ->
                                part.replaceFirstChar { it.uppercase() }
                            },
                            description = cleanupDescriptionText(model.flavorText.first().description),
                        )
                    }

                    abilitiesDao.deleteAll()
                    abilitiesDao.insertAll(*abilitiesFromServer.toTypedArray())
                    Result.Success(abilitiesFromServer)
                } else {
                    Result.Error(
                        ApolloException("The response has errors: ${response.errors}")
                    )
                }
            }
        }
    }

    override suspend fun getAbilityById(id: Int): Result<Ability> {
        abilitiesDao.findById(id)?.let {
            return Result.Success(it)
        }
        return Result.Error(
            Exception("Ability with ID: $id not found in local DB!")
        )
    }

}
