package com.garam.qook.data.repository

import com.garam.qook.data.local.LocalRecipeAnalysis
import com.garam.qook.data.local.LocalUserData

interface LoginRepository {

    suspend fun saveUserData(localUserData: LocalUserData)

    suspend fun getFBData() : List<LocalRecipeAnalysis>

    suspend fun saveFBToLocalData(analysisList : List<LocalRecipeAnalysis>)
}