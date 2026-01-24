package com.garam.qook.data

import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun saveAnalysis(analysisData: AnalysisData)

    suspend fun deleteAnalysis(analysisData: AnalysisData)

    fun getAllData() : Flow<List<AnalysisData>>

}