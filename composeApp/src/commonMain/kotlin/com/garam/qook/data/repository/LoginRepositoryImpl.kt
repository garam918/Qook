package com.garam.qook.data.repository

import com.garam.qook.data.firebase.FirebaseDataSource
import com.garam.qook.data.local.AnalysisDao
import com.garam.qook.data.local.LocalRecipeAnalysis
import com.garam.qook.data.local.LocalUserData
import com.garam.qook.data.local.UserDao

class LoginRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource,
    private val userDao: UserDao,
    private val analysisDao: AnalysisDao
) : LoginRepository {

    override suspend fun saveUserData(localUserData: LocalUserData) {

        userDao.saveUserData(localUserData)
        firebaseDataSource.setUserData(localUserData)
    }

    override suspend fun getFBData() = firebaseDataSource.getData()

    override suspend fun saveFBToLocalData(analysisList: List<LocalRecipeAnalysis>) {
        analysisDao.saveFBToLocalData(analysisList)
    }
}