package com.garam.qook.auth

import com.garam.qook.data.local.LocalUserData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl: AuthRepository{

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

            val email = user?.email
            val uid = user?.uid.toString()
            val loginType = "google"

            return LocalUserData(uid = uid, email = email, loginType = loginType)
//        }
    }

    override suspend fun signInWithApple(): LocalUserData? = null

    override suspend fun signOut() = Firebase.auth.signOut()
    

    override suspend fun deleteAccount() {
        val currentUser = Firebase.auth.currentUser

        Firebase.firestore.collection("users").document(currentUser?.uid.toString())
            .delete().await()

        currentUser?.delete()?.await()
    }

    override fun currentUser(): LocalUserData? {
        val user = Firebase.auth.currentUser

        return if(user == null) null
        else LocalUserData(uid = user.uid, email = user.email, loginType = "google")

//        val loginType = if(user?.isAnonymous == true) "anonymous"
//        else when(user?.providerData[1]?.providerId) {
//            "google.com" -> "google"
//            "apple.com" -> "apple"
//            else -> ""
//        }
//
//        return if(user == null) null else LocalUserData(uid = user.uid, email = user.email, loginType = loginType)

    }
}