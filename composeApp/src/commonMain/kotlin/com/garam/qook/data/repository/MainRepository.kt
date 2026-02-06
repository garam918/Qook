package com.garam.qook.data.repository

import com.garam.qook.data.RecipeAnalysis
import com.garam.qook.data.local.LocalGroceryData
import com.garam.qook.data.local.LocalRecipeAnalysis
import com.garam.qook.data.local.LocalUserData
import com.garam.qook.data.remote.ApiRequest
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getCurrentUser() : LocalUserData?

    suspend fun saveAnalysis(recipeAnalysis: RecipeAnalysis)

    suspend fun deleteAnalysis(localRecipeAnalysis: LocalRecipeAnalysis)

    fun getAllData(uid: String) : Flow<List<LocalRecipeAnalysis>>

    suspend fun sendUrl(apiRequest: ApiRequest) : Result<RecipeAnalysis>

    suspend fun signOut()

    suspend fun saveGrocery(localGroceryData: LocalGroceryData)

    suspend fun deleteGrocery(id: String)

    fun getGrocery() : Flow<List<LocalGroceryData>>

}