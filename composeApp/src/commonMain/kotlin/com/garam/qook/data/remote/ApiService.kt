package com.garam.qook.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.timeout
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.reflect.TypeInfo

class ApiService(private val httpClient: HttpClient) {

    suspend fun sendUrl(url: ApiRequest) : ApiResponse {
        return httpClient.post("https://blithely-nonfortifiable-marx.ngrok-free.dev/extract-recipe") {
            contentType(ContentType.Application.Json)
            setBody(url)
        }.body()
    }

}