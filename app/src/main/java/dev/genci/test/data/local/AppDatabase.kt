package dev.genci.test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.genci.test.data.local.dao.CocktailDao
import dev.genci.test.data.local.entity.CocktailEntity

@Database(entities = [CocktailEntity::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}
