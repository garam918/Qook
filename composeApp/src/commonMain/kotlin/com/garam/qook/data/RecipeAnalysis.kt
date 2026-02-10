package com.garam.qook.data

import kotlinx.serialization.Serializable

@Serializable
data class RecipeAnalysis(
    val id : String = "",
    val dish : String = "",
    val source: String = "",
    val createdTime : Long = 0L,
    val ingredients : List<IngredientCategory> = listOf(),
    val recipe : List<Recipe> = listOf()
)

@Serializable
data class IngredientCategory(
    val category: String = "",
    val items: List<IngredientItem> = listOf()
)
@Serializable
data class IngredientItem(
    val ingredient: String = "",
    val amount: String = ""
)

@Serializable
data class Recipe(
    val step : String = ""
)