package id.angga.pokemonsprout.data.items

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.angga.pokemonsprout.model.Item

@Dao
interface ItemsDao {

    @Query("SELECT * FROM item")
    suspend fun getAll(): List<Item>

    @Query("SELECT * FROM item WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Item?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: Item)

    @Query("DELETE FROM item")
    suspend fun deleteAll()
}