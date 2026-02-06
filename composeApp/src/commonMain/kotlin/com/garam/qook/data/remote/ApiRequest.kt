package com.garam.qook.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ApiRequest(
    val youtube_url : String
)