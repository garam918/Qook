package com.garam.qook.data.firebase

import cocoapods.FirebaseAuth.FIRAuth
import cocoapods.FirebaseFirestoreInternal.FIRFirestore
import com.garam.qook.data.local.LocalRecipeAnalysis
import com.garam.qook.data.local.LocalUserData
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.suspendCancellableCoroutine

class FirebaseDataSourceImpl : FirebaseDataSource{

    private val firestore = Firebase.firestore
    private val currentUser = FIRAuth.auth().currentUser()

    private val uid = currentUser?.uid().toString()

    private val userCollectionPath = "Users"
    private val recipeCollectionPath = "Recipe"


    override suspend fun setUserData(userData: LocalUserData) {
        suspendCancellableCoroutine { continuation ->
            suspend {
                firestore.collection(userCollectionPath)
                    .document(uid).set(userData, merge = true)

                continuation.resumeWith(Result.success(Unit))
            }
        }
    }

    override suspend fun saveData(localRecipeAnalysis: LocalRecipeAnalysis) {
        suspendCancellableCoroutine { continuation ->
            suspend {
                firestore.collection(userCollectionPath)
                    .document(localRecipeAnalysis.id).set(localRecipeAnalysis, merge = true)

                continuation.resumeWith(Result.success(Unit))
            }
        }


    }

    override suspend fun getData(): List<LocalRecipeAnalysis> = firestore.collection(userCollectionPath)
        .document(uid).collection(recipeCollectionPath)
        .get().documents.map { it.data<LocalRecipeAnalysis>() }

    override suspend fun deleteData(id: String) {
        suspendCancellableCoroutine { continuation ->
            suspend {
                firestore.collection(userCollectionPath)
                    .document(uid).collection(recipeCollectionPath)
                    .document(id).delete()

                continuation.resumeWith(Result.success(Unit))
            }
        }

    }
}