package com.garam.qook.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val success : Boolean,
    val recipe: List<NetworkRecipeAnalysis>,
//    val networkRecipeAnalysis: NetworkRecipeAnalysis,
    val source: String,
    val analysis_method: String,
    val method_description: String

)

@Serializable
data class ApiFailure(
    val success: Boolean,
    val error: String,
    val message: String,
    val source: String,
    val analysis_method : String


)