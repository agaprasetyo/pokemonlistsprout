package id.angga.pokemonsprout.data.items

import id.angga.pokemonsprout.model.Item

interface ItemsRepository {
    suspend fun getAllItems(): Result<List<Item>>
    suspend fun getItemById(id: Int): Result<Item>
}