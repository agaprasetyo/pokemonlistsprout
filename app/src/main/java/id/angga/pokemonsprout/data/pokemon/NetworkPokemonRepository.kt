package id.angga.pokemonsprout.data.pokemon

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import id.angga.pokemonsprout.PokemonOriginalQuery
import id.angga.pokemonsprout.data.Result
import id.angga.pokemonsprout.data.cleanupDescriptionText
import id.angga.pokemonsprout.model.Evolution
import id.angga.pokemonsprout.model.EvolutionTrigger
import id.angga.pokemonsprout.model.Pokemon
import id.angga.pokemonsprout.model.PokemonAbility
import id.angga.pokemonsprout.model.PokemonMove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkPokemonRepository @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val apolloClient: ApolloClient
) :
    PokemonRepository {
    override suspend fun getAllPokemon(): Result<List<Pokemon>> {
        val localPokemon = pokemonDao.getAll()

        if (localPokemon.isNotEmpty()) {
            delay(300)
            println("from cache")
            return Result.Success(localPokemon)
        } else {
            return withContext(Dispatchers.IO) {
                println("from network")
                val response = apolloClient.query(PokemonOriginalQuery()).execute()

                if (!response.hasErrors()) {
                    val pokemonFromServer = response.data!!.pokemon.map { model ->
                        val detail = model.detail.first()
                        val stats = detail.stats.map { it.baseStat }

                        Pokemon(
                            id = model.id,
                            name = formatName(model.name),
                            description = cleanupDescriptionText(model.description.first().flavorText)
                                .replace(model.name.uppercase(), formatName(model.name)),
                            typeOfPokemon = detail.types.map { formatName(it.type!!.name) },
                            category = model.species[0].genus,
                            image = model.id,
                            height = (detail.height ?: 0) / 10.0, // in decimeters
                            weight = (detail.weight ?: 0) / 10.0, // in 10 gram chunks
                            genderRate = model.genderRate ?: -1,
                            hp = stats[0],
                            attack = stats[1],
                            defense = stats[2],
                            specialAttack = stats[3],
                            specialDefense = stats[4],
                            speed = stats[5],
                            evolutionChain = transformEvolutionChain(
                                model.evolutionChain?.evolutions ?: emptyList()
                            ),
                            movesList = transformMoves(detail.moves),
                            abilitiesList = transformAbilities(detail.abilities)
                        )
                    }

                    pokemonDao.deleteAll()
                    pokemonDao.insertAll(*pokemonFromServer.toTypedArray())
                    Result.Success(pokemonFromServer)
                } else {
                    Result.Error(
                        ApolloException("The response has errors: ${response.errors}")
                    )
                }
            }
        }
    }

    override suspend fun getPokemonById(id: Int): Result<Pokemon> {
        TODO("Not yet implemented")
    }

}

private fun transformEvolutionChain(
    list: List<PokemonOriginalQuery.Evolution>
): List<Evolution> {
    return list
        .map {
            if (it.targetLevels.isNotEmpty()) {
                val target = it.targetLevels.first()

                Evolution(
                    id = it.id,
                    targetLevel = target.level ?: -1,
                    trigger = when (target.triggerType) {
                        3 -> EvolutionTrigger.UseItem
                        2 -> EvolutionTrigger.Trade
                        else -> EvolutionTrigger.LevelUp
                    },
                    itemId = target.itemId ?: -1
                )
            } else {
                Evolution(id = it.id)
            }
        }
}

private fun transformMoves(
    list: List<PokemonOriginalQuery.Move>
): List<PokemonMove> {
    return list
        .map {
            PokemonMove(it.id!!, it.level)
        }
}

private fun transformAbilities(
    list: List<PokemonOriginalQuery.Ability>
): List<PokemonAbility> {
    return list
        .map {
            PokemonAbility(it.id!!, it.isHidden)
        }
}


private fun formatName(
    name: String
): String {
    return name.replaceFirstChar { it.uppercase() }
}
