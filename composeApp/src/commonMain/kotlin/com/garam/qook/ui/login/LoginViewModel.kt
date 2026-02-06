package com.garam.qook.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garam.qook.auth.AuthRepository
import com.garam.qook.data.local.LocalRecipeAnalysis
import com.garam.qook.data.local.LocalUserData
import com.garam.qook.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(

    private val authRepository: AuthRepository,
    private val loginRepository: LoginRepository


) : ViewModel() {

    private val _savedFBList = MutableStateFlow<List<LocalRecipeAnalysis>>(listOf())
    val savedFBList = _savedFBList.asStateFlow()

    suspend fun googleLogin(idToken : String, accessToken: String) : LocalUserData? = authRepository.signInWithGoogle(idToken = idToken, accessToken = accessToken)
    suspend fun appleLogin() : LocalUserData? = authRepository.signInWithApple()

    suspend fun isExistUser(uid: String) = authRepository.isExistAccount(uid)

    fun saveUserData(userData: LocalUserData) = viewModelScope.launch {

        loginRepository.saveUserData(userData)
    }

    fun getFBData() = viewModelScope.launch {
        _savedFBList.value = loginRepository.getFBData()
    }

    fun saveFBToLocalData() = viewModelScope.launch{

        loginRepository.saveFBToLocalData(savedFBList.value)

    }



}