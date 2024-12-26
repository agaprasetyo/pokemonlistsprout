package id.angga.pokemonsprout.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.angga.pokemonsprout.data.abilities.AbilitiesDao
import id.angga.pokemonsprout.data.items.ItemsDao
import id.angga.pokemonsprout.data.moves.MovesDao
import id.angga.pokemonsprout.data.pokemon.PokemonDao
import id.angga.pokemonsprout.model.Item
import id.angga.pokemonsprout.model.Move
import id.angga.pokemonsprout.model.Pokemon
import id.angga.pokemonsprout.model.Ability

@Database(
    version = 6,
    entities = [Pokemon::class, Move::class, Item::class, Ability::class],
    autoMigrations = [
        AutoMigration(from = 1, to = 6),
    ]
)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun movesDao(): MovesDao
    abstract fun itemsDao(): ItemsDao
    abstract fun abilitiesDao(): AbilitiesDao
}

class Converters {

}