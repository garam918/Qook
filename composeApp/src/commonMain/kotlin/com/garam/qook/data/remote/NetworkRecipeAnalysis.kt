package com.garam.qook.data.remote

import com.garam.qook.data.IngredientCategory
import com.garam.qook.data.Recipe
import kotlinx.serialization.Serializable

@Serializable
data class NetworkRecipeAnalysis(
    val dish : String,
    val source: String = "",
    val ingredients : List<IngredientCategory>,
    val recipe: List<Recipe>
)