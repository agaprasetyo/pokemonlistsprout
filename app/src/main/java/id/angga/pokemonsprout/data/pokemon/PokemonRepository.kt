package id.angga.pokemonsprout.data.pokemon

import id.angga.pokemonsprout.model.Pokemon
import id.angga.pokemonsprout.data.Result

interface PokemonRepository {

    suspend fun getAllPokemon(): Result<List<Pokemon>>

    suspend fun getPokemonById(id: Int): Result<Pokemon>
}