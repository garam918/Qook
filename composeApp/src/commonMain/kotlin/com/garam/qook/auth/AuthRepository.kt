package com.garam.qook.auth

import com.garam.qook.data.local.LocalUserData

interface AuthRepository {

    fun isLoggedIn() : Boolean
    suspend fun isExistAccount(uid: String) : Boolean

    suspend fun signInWithGoogle(idToken: String, accessToken : String): LocalUserData?

    suspend fun signInWithApple(): LocalUserData?

    suspend fun signOut()

    suspend fun deleteAccount()

    suspend fun currentUser(): LocalUserData?

    suspend fun updateUserInfo(userInfo: LocalUserData)

}