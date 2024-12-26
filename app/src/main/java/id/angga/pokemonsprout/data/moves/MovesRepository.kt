package id.angga.pokemonsprout.data.moves

import id.angga.pokemonsprout.model.Move

interface MovesRepository {
    suspend fun getAllMoves(): Result<List<Move>>
    suspend fun getMoveById(id: Int): Result<Move>
}