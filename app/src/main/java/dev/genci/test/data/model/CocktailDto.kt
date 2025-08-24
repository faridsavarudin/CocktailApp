package dev.genci.test.data.model

import com.google.gson.annotations.SerializedName

data class CocktailDto(
    @SerializedName("idDrink") val idDrink: String,
    @SerializedName("strDrink") val strDrink: String,
    @SerializedName("strCategory") val strCategory: String?,
    @SerializedName("strInstructions") val strInstructions: String?,
    @SerializedName("strDrinkThumb") val strDrinkThumb: String?,
    @SerializedName("strGlass") val strGlass: String?
)