package com.garam.qook.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalysisDao {

    @Insert
    suspend fun saveAnalysis(localRecipeAnalysis: LocalRecipeAnalysis)

    @Query("DELETE FROM analysis WHERE id = :id")
    suspend fun deleteAnalysis(id : String)

    @Query("SELECT * FROM analysis WHERE uid = :uid")
    fun getAll(uid: String) : Flow<List<LocalRecipeAnalysis>>

    @Upsert
    suspend fun saveFBToLocalData(analysisList: List<LocalRecipeAnalysis>)
}