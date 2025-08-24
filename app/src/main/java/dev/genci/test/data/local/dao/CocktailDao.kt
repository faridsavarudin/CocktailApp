package dev.genci.test.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.genci.test.data.local.entity.CocktailEntity

@Dao
interface CocktailDao {

    @Query("SELECT * FROM cocktails ORDER BY strDrink ASC")
    fun observeAll(): LiveData<List<CocktailEntity>>

    @Query("SELECT * FROM cocktails WHERE idDrink = :id LIMIT 1")
    fun observeById(id: String): LiveData<CocktailEntity?>

    @Query("SELECT * FROM cocktails WHERE strDrink LIKE '%' || :query || '%' ORDER BY strDrink ASC")
    fun observeSearch(query: String): LiveData<List<CocktailEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cocktails: List<CocktailEntity>)

    @Query("DELETE FROM cocktails")
    suspend fun clear()
}