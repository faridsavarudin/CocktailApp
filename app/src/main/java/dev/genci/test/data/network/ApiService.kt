package dev.genci.test.data.network

import dev.genci.test.data.model.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search.php")
    suspend fun searchByName(@Query("s") name: String): CocktailResponse

    @GET("lookup.php")
    suspend fun lookupById(@Query("i") id: String): CocktailResponse
}