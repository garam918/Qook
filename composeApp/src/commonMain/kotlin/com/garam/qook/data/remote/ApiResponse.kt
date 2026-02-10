package com.garam.qook.data.remote

import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResponse {

    @Serializable
    data class ApiSuccess(
        val success: Boolean,
        val recipe: List<NetworkRecipeAnalysis>,
        val source: String,
        val analysis_method: String,
        val method_description: String
    ) : ApiResponse()

    @Serializable
    data class ApiFailure(
        val success: Boolean,
        val error: String,
        val message: String,
        val source: String,
        val analysis_method: String
    ) : ApiResponse()

}