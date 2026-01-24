package com.garam.qook.ui.login

import androidx.lifecycle.ViewModel
import com.garam.qook.auth.AuthRepository
import com.garam.qook.data.local.LocalUserData

class LoginViewModel(

    private val authRepository: AuthRepository

) : ViewModel() {


    suspend fun googleLogin(idToken : String, accessToken: String) : LocalUserData? = authRepository.signInWithGoogle(idToken = idToken, accessToken = accessToken)
    suspend fun appleLogin() : LocalUserData? = authRepository.signInWithApple()



}