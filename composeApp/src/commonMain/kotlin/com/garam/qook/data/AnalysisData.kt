package com.garam.qook.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Entity(
    tableName = "analysis"
)
data class AnalysisData(
    @PrimaryKey
    val id: String = Uuid.random().toString(),
    val title : String,
    val videoUrl : String,
    val cookingImgUrl : String,
    val createdTime: Long,
    val ingredientsList : List<Ingredient>
)

@Serializable
data class Ingredient(
    val name: String,
    val mount : String,
    val category : String // enum 클래스로
)
