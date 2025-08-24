package dev.genci.test.data.model

import dev.genci.test.data.local.entity.CocktailEntity

fun CocktailDto.toEntity() = CocktailEntity(
    idDrink = idDrink,
    strDrink = strDrink,
    strCategory = strCategory,
    strInstructions = strInstructions,
    strDrinkThumb = strDrinkThumb,
    strGlass = strGlass
)

fun CocktailEntity.toUiModel() = UiCocktail(
    id = idDrink,
    name = strDrink,
    category = strCategory,
    instructions = strInstructions,
    thumb = strDrinkThumb,
    strGlass = strGlass
)



data class UiCocktail(
    val id: String,
    val name: String,
    val category: String?,
    val instructions: String?,
    val thumb: String?,
    val strGlass: String?
)

