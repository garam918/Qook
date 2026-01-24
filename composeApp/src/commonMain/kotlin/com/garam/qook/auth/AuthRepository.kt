package com.garam.qook.auth

import com.garam.qook.data.local.LocalUserData

interface AuthRepository {

    suspend fun signInWithGoogle(idToken: String, accessToken : String): LocalUserData?

    suspend fun signInWithApple(): LocalUserData?

    suspend fun signOut()

    suspend fun deleteAccount()

    fun currentUser(): LocalUserData?

}