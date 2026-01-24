package com.garam.qook.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalysisDao {

    @Insert
    suspend fun saveAnalysis(analysisData: AnalysisData)

    @Query("DELETE FROM analysis WHERE id = :id")
    suspend fun deleteAnalysis(id : String)

    @Query("SELECT * FROM analysis")
    fun getAll() : Flow<List<AnalysisData>>
}