package id.angga.pokemonsprout.data.moves

import id.angga.pokemonsprout.model.Move
import id.angga.pokemonsprout.data.Result

interface MovesRepository {
    suspend fun getAllMoves(): Result<List<Move>>
    suspend fun getMoveById(id: Int): Result<Move>
}