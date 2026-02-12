package com.garam.qook.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "grocery_data")
@Serializable
data class LocalGroceryData(
    @PrimaryKey
    val id : String,
    val ingredientName: String,
)
