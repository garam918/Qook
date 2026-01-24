package com.garam.qook.data

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class AnalysisTypeConverter {

    @TypeConverter
    fun fromString(value: String) : List<Ingredient> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromMap(list: List<Ingredient>) : String {
        return Json.encodeToString(list)
    }
}