package id.angga.pokemonsprout.data.moves

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import id.angga.pokemonsprout.PokemonOriginalMovesQuery
import id.angga.pokemonsprout.data.Result
import id.angga.pokemonsprout.data.cleanupDescriptionText
import id.angga.pokemonsprout.model.Move
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkMovesRepository @Inject constructor(
    private val movesDao: MovesDao,
    private val apolloClient: ApolloClient
) :
    MovesRepository {
    override suspend fun getAllMoves(): Result<List<Move>> {
        val localMoves = movesDao.getAll()

        if (localMoves.isNotEmpty()) {
            delay(300)
            println("moves from cache")
            return Result.Success(localMoves)
        } else {
            return withContext(Dispatchers.IO) {
                println("moves from network")
                val response = apolloClient.query(PokemonOriginalMovesQuery()).execute()

                if (!response.hasErrors()) {
                    val movesFromServer = response.data!!.moves.map { model ->
                        Move(
                            id = model.id,
                            name = model.name.split("-").joinToString(" ") { part ->
                                part.replaceFirstChar { it.uppercase() }
                            },
                            description = cleanupDescriptionText(model.description.first().flavorText),
                            category = model.category!!.name.replaceFirstChar { it.uppercase() },
                            type = model.type!!.name.replaceFirstChar { it.uppercase() },
                            pp = model.pp!!,
                            power = model.power,
                            accuracy = model.accuracy
                        )
                    }

                    movesDao.deleteAll()
                    movesDao.insertAll(*movesFromServer.toTypedArray())
                    Result.Success(movesFromServer)
                } else {
                    Result.Error(
                        ApolloException("The response has errors: ${response.errors}")
                    )
                }
            }
        }
    }

    override suspend fun getMoveById(id: Int): Result<Move> {
        movesDao.findById(id)?.let {
            return Result.Success(it)
        }
        return Result.Error(
            Exception("Move with ID: $id not found in local DB!")
        )
    }

}
