package com.garam.qook.data.local

import androidx.room.TypeConverter
import com.garam.qook.data.IngredientCategory
import com.garam.qook.data.IngredientItem
import com.garam.qook.data.Recipe
import kotlinx.serialization.json.Json

class AnalysisTypeConverter {

    @TypeConverter
    fun fromCategoryString(value: String) : List<IngredientCategory> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromCategoryMap(list: List<IngredientCategory>) : String {
        return Json.encodeToString(list)
    }
    @TypeConverter
    fun fromString(value: String) : List<IngredientItem> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromMap(list: List<IngredientItem>) : String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun fromRecipeString(value: String) : List<Recipe> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromRecipeMap(list: List<Recipe>) : String {
        return Json.encodeToString(list)
    }
}