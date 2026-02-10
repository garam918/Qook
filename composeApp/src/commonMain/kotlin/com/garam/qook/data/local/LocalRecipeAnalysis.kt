package com.garam.qook.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.garam.qook.data.IngredientCategory
import com.garam.qook.data.Recipe
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Serializable
@Entity(
    tableName = "analysis"
)
data class LocalRecipeAnalysis(
    @PrimaryKey
    val id: String = "",
    val uid: String = "",
    val dish : String = "",
    val source: String = "",
    val createdTime: Long = 0L,
    val ingredients : List<IngredientCategory> = listOf(),
    val recipe : List<Recipe> = listOf()
)