package com.garam.qook.data.repository

import com.garam.qook.auth.AuthRepository
import com.garam.qook.data.RecipeAnalysis
import com.garam.qook.data.firebase.FirebaseDataSource
import com.garam.qook.data.local.AnalysisDao
import com.garam.qook.data.local.GroceryDao
import com.garam.qook.data.local.LocalGroceryData
import com.garam.qook.data.local.LocalRecipeAnalysis
import com.garam.qook.data.local.LocalUserData
import com.garam.qook.data.remote.ApiRequest
import com.garam.qook.data.remote.ApiResponse
import com.garam.qook.data.remote.ApiService
import com.garam.qook.data.toLocal
import com.garam.qook.data.toUI
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(
    private val analysisDao: AnalysisDao,
    private val apiService: ApiService,
    private val firebaseDataSource: FirebaseDataSource,
    private val authRepository : AuthRepository,
    private val groceryDao: GroceryDao
) : MainRepository {

    override suspend fun getCurrentUser(): LocalUserData? = authRepository.currentUser()

    override suspend fun saveAnalysis(recipeAnalysis: RecipeAnalysis) {
        val uid = authRepository.currentUser()?.uid.toString()
        val localRecipeAnalysis = recipeAnalysis.toLocal(uid)

        analysisDao.saveAnalysis(localRecipeAnalysis)
        firebaseDataSource.saveData(localRecipeAnalysis)
    }

    override suspend fun deleteAnalysis(localRecipeAnalysis: LocalRecipeAnalysis) {
        analysisDao.deleteAnalysis(localRecipeAnalysis.id)
        firebaseDataSource.deleteData(localRecipeAnalysis.id)
    }

    override fun getAllData(uid: String): Flow<List<LocalRecipeAnalysis>> =
        analysisDao.getAll(uid)


    override suspend fun sendUrl(apiRequest: ApiRequest): Result<RecipeAnalysis?> = try {

        val response = apiService.sendUrl(apiRequest)
        println("send url response: ${response}")
        Result.success(response.recipe[0].toLocal(response.source).toUI())
//        when(response) {
//            is ApiResponse.ApiSuccess -> Result.success(response.recipe[0].toLocal().toUI())
//            is ApiResponse.ApiFailure -> Result.success(null)
//        }


    } catch (e: Exception) {
        println("sendUrl error:${e.message}")
        Result.failure(e)
    }

    override suspend fun signOut() {
        authRepository.signOut()
    }

    override suspend fun deleteAccount() {
        authRepository.deleteAccount()
    }

    override suspend fun saveGrocery(localGroceryData: LocalGroceryData) {
        groceryDao.upsertGrocery(localGroceryData)
    }

    override suspend fun deleteGrocery(id: String) {
        groceryDao.deleteGrocery(id)
    }

    override fun getGrocery(): Flow<List<LocalGroceryData>> = groceryDao.getGroceryList()

    override suspend fun updateUserData(localUserData: LocalUserData) = authRepository.updateUserInfo(localUserData)

}