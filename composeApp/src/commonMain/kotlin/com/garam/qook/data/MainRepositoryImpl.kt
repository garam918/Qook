package com.garam.qook.data

import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(
    private val analysisDao: AnalysisDao
) : MainRepository {

    override suspend fun saveAnalysis(analysisData: AnalysisData) = analysisDao.saveAnalysis(analysisData)

    override suspend fun deleteAnalysis(analysisData: AnalysisData) = analysisDao.deleteAnalysis(analysisData.id)

    override fun getAllData(): Flow<List<AnalysisData>> =
        analysisDao.getAll()
}