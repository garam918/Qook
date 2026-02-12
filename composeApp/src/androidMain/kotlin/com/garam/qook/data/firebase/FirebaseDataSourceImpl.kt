package com.garam.qook.com.garam.qook.data.firebase

import com.garam.qook.data.firebase.FirebaseDataSource
import com.garam.qook.data.local.LocalRecipeAnalysis
import com.garam.qook.data.local.LocalUserData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseDataSourceImpl : FirebaseDataSource {

    private val firestore = FirebaseFirestore.getInstance()
    private val currentUser = Firebase.auth.currentUser
    private val uid = currentUser?.uid.toString()

    private val userCollectionPath = "Users"
    private val recipeCollectionPath = "Recipe"


    override suspend fun setUserData(userData: LocalUserData) {
        val currentUser = Firebase.auth.currentUser
        val uid = currentUser?.uid.toString()

        firestore.collection(userCollectionPath)
            .document(uid).set(userData)
    }

    override suspend fun saveData(localRecipeAnalysis: LocalRecipeAnalysis) {
        val currentUser = Firebase.auth.currentUser
        val uid = currentUser?.uid.toString()

        firestore.collection(userCollectionPath)
            .document(uid).collection(recipeCollectionPath)
            .document(localRecipeAnalysis.id).set(localRecipeAnalysis)
    }

    override suspend fun getData(): List<LocalRecipeAnalysis> {
        val currentUser = Firebase.auth.currentUser
        val uid = currentUser?.uid.toString()

        return firestore.collection(userCollectionPath)
            .document(uid).collection(recipeCollectionPath).get().await().toObjects(
                LocalRecipeAnalysis::class.java
            )
    }

    override suspend fun deleteData(id: String) {
        val currentUser = Firebase.auth.currentUser
        val uid = currentUser?.uid.toString()

        firestore.collection(userCollectionPath).document(uid)
            .collection(recipeCollectionPath)
            .document(id).delete()

    }
}