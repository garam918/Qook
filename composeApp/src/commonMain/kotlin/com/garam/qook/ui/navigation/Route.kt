package com.garam.qook.ui.navigation

import com.garam.qook.data.RecipeAnalysis


sealed interface Route {
    data object RouteOnBoarding

    data object RouteHome

    data class RouteResult(val data: RecipeAnalysis) : Route

    data object RoutePaywall

    data object RouteGrocery
}
