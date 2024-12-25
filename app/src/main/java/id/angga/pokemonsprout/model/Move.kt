package id.angga.pokemonsprout.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Move(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val type: String,
    val pp: Int,
    val power: Int?,
    val accuracy: Int?
)