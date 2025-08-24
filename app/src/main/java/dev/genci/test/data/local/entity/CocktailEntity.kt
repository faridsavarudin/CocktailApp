package dev.genci.test.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailEntity(
    @PrimaryKey val idDrink: String,
    val strDrink: String,
    val strCategory: String?,
    val strInstructions: String?,
    val strDrinkThumb: String?,
    val strGlass: String?
)