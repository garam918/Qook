package com.garam.qook.ui.navigation

import com.garam.qook.data.RecipeAnalysis
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


sealed interface Route {
    data object RouteOnBoarding

    @OptIn(ExperimentalUuidApi::class)
    data class RouteHome(val id: String = Uuid.random().toString())

    data class RouteResult(val data: RecipeAnalysis) : Route

    data object RoutePaywall

    data object RouteGrocery
}
