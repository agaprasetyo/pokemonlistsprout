package id.angga.pokemonsprout.data.abilities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.angga.pokemonsprout.model.Ability

@Dao
interface AbilitiesDao {

    @Query("SELECT * FROM ability")
    suspend fun getAll(): List<Ability>

    @Query("SELECT * FROM ability WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Ability?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: Ability)

    @Query("DELETE FROM ability")
    suspend fun deleteAll()
}