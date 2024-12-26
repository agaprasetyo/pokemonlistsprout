package id.angga.pokemonsprout.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.angga.pokemonsprout.data.moves.MovesDao
import id.angga.pokemonsprout.data.moves.MovesRepository
import id.angga.pokemonsprout.data.moves.NetworkMovesRepository

@InstallIn(SingletonComponent::class)
@Module
object NetworkMovesRepositoryModule {

    @Provides
    fun provideMovesRepository(
        movesDao: MovesDao, apolloClient: ApolloClient
    ): MovesRepository {
        return NetworkMovesRepository(movesDao, apolloClient)
    }
}
