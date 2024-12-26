package id.angga.pokemonsprout.data.abilities

import id.angga.pokemonsprout.model.Ability
import id.angga.pokemonsprout.data.Result

interface AbilitiesRepository {
    suspend fun getAllAbilities(): Result<List<Ability>>
    suspend fun getAbilityById(id: Int): Result<Ability>
}