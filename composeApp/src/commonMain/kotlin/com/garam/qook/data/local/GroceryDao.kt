package com.garam.qook.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {

    @Upsert
    suspend fun upsertGrocery(localGroceryData: LocalGroceryData)

    @Query("DELETE FROM grocery_data WHERE id = :id")
    suspend fun deleteGrocery(id: String)

    @Query("SELECT * FROM grocery_data")
    fun getGroceryList() : Flow<List<LocalGroceryData>>

}