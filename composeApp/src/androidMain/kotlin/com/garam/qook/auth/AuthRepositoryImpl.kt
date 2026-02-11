package com.garam.qook.auth

import com.garam.qook.data.local.LocalUserData
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

class AuthRepositoryImpl() : AuthRepository {


    override fun isLoggedIn(): Boolean = Firebase.auth.currentUser != null

    override suspend fun isExistAccount(uid: String): Boolean = Firebase.firestore.collection("Users").document(uid).get().await().exists()

    override suspend fun signInWithGoogle(
        idToken: String,
        accessToken: String
    ): LocalUserData? {
        val googleCredential = GoogleAuthProvider.getCredential(idToken, null)


        println("google credential ${googleCredential.provider}")
//        if(Firebase.auth.currentUser != null && Firebase.auth.currentUser?.isAnonymous == true) {
//
//            val task = Firebase.auth.currentUser?.linkWithCredential(googleCredential)
//
//            var user : FirebaseUser?
//
//            if(task?.isSuccessful == true) {
//                user = Firebase.auth.currentUser?.linkWithCredential(googleCredential)?.await()?.user
//            }
//            else {
//                user = Firebase.auth.signInWithCredential(googleCredential).await().user
//            }
//
//            println("google user $user")
//
//
//            val email = user?.email
//            val uid = user?.uid.toString()
//            val loginType = "google"
//
//            return LocalUserData(uid = uid, email = email, loginType = loginType)
//        }
//        else {

        val user = Firebase.auth.signInWithCredential(googleCredential).await().user

        val email = user?.email ?: user?.providerData[1]?.email
        val uid = user?.uid.toString()
        val loginType = "google"

        return if (isExistAccount(uid)) Firebase.firestore.collection("Users").document(uid)
                .get().await().toObject(LocalUserData::class.java)
        else LocalUserData(uid = uid, email = email, loginType = loginType, paid = false, lastUseDate = Clock.System.todayIn(
            TimeZone.currentSystemDefault()).toString(), usageCount = 3)

//        }
    }

    override suspend fun signInWithApple(): LocalUserData? = null

    override suspend fun signOut() = Firebase.auth.signOut()


    override suspend fun deleteAccount() {
        val currentUser = Firebase.auth.currentUser

        Firebase.firestore.collection("Users").document(currentUser?.uid.toString())
            .delete().await()

        currentUser?.delete()?.await()
    }

    override suspend fun currentUser(): LocalUserData? {

        val user = Firebase.auth.currentUser

        return if (user == null) null
        else {
            Firebase.firestore.collection("Users").document(user.uid)
                .get().await().toObject(LocalUserData::class.java)
        }

//        val loginType = if(user?.isAnonymous == true) "anonymous"
//        else when(user?.providerData[1]?.providerId) {
//            "google.com" -> "google"
//            "apple.com" -> "apple"
//            else -> ""
//        }
//
//        return if(user == null) null else LocalUserData(uid = user.uid, email = user.email, loginType = loginType)
    }

    override suspend fun updateUserInfo(userInfo: LocalUserData) {

        Firebase.firestore.collection("Users").document(userInfo.uid)
            .update("paid", userInfo.paid, "lastUseDate", userInfo.lastUseDate, "usageCount", userInfo.usageCount)
    }
}