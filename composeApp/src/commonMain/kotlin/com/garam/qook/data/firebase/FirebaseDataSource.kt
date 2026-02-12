package com.garam.qook.data.firebase

import com.garam.qook.data.local.LocalRecipeAnalysis
import com.garam.qook.data.local.LocalUserData

interface FirebaseDataSource {

    suspend fun setUserData(userData: LocalUserData)

    suspend fun saveData(localRecipeAnalysis: LocalRecipeAnalysis)

    suspend fun getData() : List<LocalRecipeAnalysis>

    suspend fun deleteData(id: String)

}