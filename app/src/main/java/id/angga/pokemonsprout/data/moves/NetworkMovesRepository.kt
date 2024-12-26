package id.angga.pokemonsprout.data.moves

import com.apollographql.apollo3.ApolloClient
import id.angga.pokemonsprout.data.Result
import id.angga.pokemonsprout.model.Move

class NetworkMovesRepository(movesDao: MovesDao, apolloClient: ApolloClient) :
    MovesRepository {
    override suspend fun getAllMoves(): Result<List<Move>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMoveById(id: Int): Result<Move> {
        TODO("Not yet implemented")
    }

}
