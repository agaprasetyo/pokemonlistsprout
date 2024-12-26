package id.angga.pokemonsprout.data.moves

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.angga.pokemonsprout.model.Move

@Dao
interface MovesDao {

    @Query("SELECT * FROM move")
    suspend fun getAll(): List<Move>

    @Query("SELECT * FROM move WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Move?

    @Query("SELECT * FROM move WHERE id IN (:ids)")
    suspend fun findByIds(ids: List<Int>): List<Move>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg move: Move)

    @Query("DELETE FROM move")
    suspend fun deleteAll()
}