package id.angga.pokemonsprout.data.items

import com.apollographql.apollo3.ApolloClient
import id.angga.pokemonsprout.data.Result
import id.angga.pokemonsprout.model.Item

class NetworkItemsRepository(itemsDao: ItemsDao, apolloClient: ApolloClient) :
    ItemsRepository {
    override suspend fun getAllItems(): Result<List<Item>> {
        TODO("Not yet implemented")
    }

    override suspend fun getItemById(id: Int): Result<Item> {
        TODO("Not yet implemented")
    }

}
