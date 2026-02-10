package com.garam.qook

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.garam.qook.revenueCatTest.RevenueCatRepository
import com.garam.qook.ui.groceryList.GroceryListScreen
import com.garam.qook.ui.home.HomeScreen
import com.garam.qook.ui.navigation.Route
import com.garam.qook.ui.onboarding.OnBoardingScreen
import com.garam.qook.ui.paywall.PaywallScreen
import com.garam.qook.ui.result.AnalysisScreen
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(mainViewModel: MainViewModel = koinViewModel()) {

    val currentUser = mainViewModel.currentUser.collectAsState()

    val isLoggedIn = mainViewModel.getLoggedIn()

    val revenueCatRepo = remember { RevenueCatRepository() }

    val updateUserScope = rememberCoroutineScope()

    var appSessionId by remember { mutableStateOf(0) }

    LaunchedEffect(currentUser.value) {

        val isActiveSubscription = revenueCatRepo.hasActiveEntitlement()

        println("isActiveSubscription $isActiveSubscription")

        println("currentUser ${currentUser.value}")

        currentUser.value?.let { user ->
            updateUserScope.launch {
                mainViewModel.updateUser(user.copy(paid = isActiveSubscription))
            }
        }
    }

    key(appSessionId) {
        MaterialTheme {
            val backStack =
                remember { mutableStateListOf(if (isLoggedIn) Route.RouteHome() else Route.RouteOnBoarding) }
//            remember { mutableStateListOf(if (true) Route.RouteHome() else Route.RouteOnBoarding) }
            NavDisplay(
                backStack = backStack,
                onBack = { if (backStack.size > 1) backStack.removeLastOrNull() },
                entryProvider = entryProvider {

                    entry<Route.RouteOnBoarding> {
                        OnBoardingScreen(onNavigateToHome = {
                            backStack.clear()
                            backStack.add(it)
                        })
                    }

                    entry<Route.RouteHome> {
                        HomeScreen(onNavigationToResult = {
                            backStack.add(Route.RouteResult(data = it))
                        }, onNavigationToPaywall = {
                            backStack.add(Route.RoutePaywall)
                        }, onNavigationToOnboarding = {
                            backStack.clear()
                            backStack.add(Route.RouteOnBoarding)
                        }, onNavigationToGrocery = {

                            backStack.add(Route.RouteGrocery)

                        })
                    }

                    entry<Route.RouteResult> { key ->

                        AnalysisScreen(onBackBtn = {
                            backStack.remove(key)
                        }, data = key.data, isPaid = currentUser.value?.paid ?: false)
                    }

                    entry<Route.RoutePaywall> { key ->
                        PaywallScreen(
                            revenueCatRepo,
                            onDismiss = {
                                backStack.remove(key)
                            },
                            onPurchaseComplete = { currentUser ->

                                updateUserScope.launch {
                                    mainViewModel.updateUser(currentUser.copy(paid = true))
                                }.invokeOnCompletion {
                                    backStack.clear()
                                    backStack.add(Route.RouteHome())
                                    appSessionId++
                                }
                            },
                            currentUser = currentUser.value ?: return@entry,
                        )
                    }

                    entry<Route.RouteGrocery> {
                        GroceryListScreen(
                            onBackBtn = {
                                backStack.remove(it)
                            }
                        )
                    }
                },

                )
        }
    }
}