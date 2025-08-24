package dev.genci.test.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import dev.genci.test.data.local.dao.CocktailDao
import dev.genci.test.data.model.UiCocktail
import dev.genci.test.data.model.toEntity
import dev.genci.test.data.model.toUiModel
import dev.genci.test.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class CocktailRepository(
    private val api: ApiService,
    private val dao: CocktailDao
) {
    val cocktails: LiveData<List<UiCocktail>> =
        dao.observeAll().map { list -> list.map { it.toUiModel() } }

    fun cocktailById(id: String): LiveData<UiCocktail?> =
        dao.observeById(id).map { it?.toUiModel() }

    fun searchLocal(query: String): LiveData<List<UiCocktail>> =
        dao.observeSearch(query).map { list -> list.map { it.toUiModel() } }

    suspend fun refreshByName(query: String) {
        try {
            val remote = withContext(Dispatchers.IO) {
                api.searchByName(query).drinks.orEmpty()
            }
            dao.insertAll(remote.map { it.toEntity() })
        } catch (e: IOException) {
            // offline / network error
            Log.e("CocktailRepository", "Unable to fetch cocktails by name. Using local DB.", e)
        } catch (e: Exception) {
            // error lain
            Log.e("CocktailRepository", "Unexpected error in refreshByName", e)
        }
    }

    suspend fun refreshDetail(id: String) {
        try {
            val remote = withContext(Dispatchers.IO) {
                api.lookupById(id).drinks.orEmpty()
            }
            dao.insertAll(remote.map { it.toEntity() })
        } catch (e: IOException) {
            Log.e("CocktailRepository", "Unable to fetch cocktail detail. Using local DB.", e)
        } catch (e: Exception) {
            Log.e("CocktailRepository", "Unexpected error in refreshDetail", e)
        }
    }
}