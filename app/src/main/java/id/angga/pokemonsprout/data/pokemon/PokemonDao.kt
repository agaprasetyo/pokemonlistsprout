package id.angga.pokemonsprout.data.pokemon

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.angga.pokemonsprout.model.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    suspend fun getAll(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Pokemon?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg pokemon: Pokemon)

    @Delete
    suspend fun delete(pokemon: Pokemon)

    @Query("DELETE FROM pokemon")
    suspend fun deleteAll()
}