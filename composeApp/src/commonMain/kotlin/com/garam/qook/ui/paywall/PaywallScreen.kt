package com.garam.qook.ui.paywall

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.garam.qook.data.local.LocalUserData
import com.garam.qook.revenueCatTest.RevenueCatRepository
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallListener
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions

@Composable
fun PaywallScreen(
    revenueCatRepo: RevenueCatRepository,
    onPurchaseComplete: (LocalUserData) -> Unit,
    onDismiss : () -> Unit,
    currentUser: LocalUserData
) {

    // 로딩/상품 상태
    var offerings by remember { mutableStateOf<List<StoreProduct>>(emptyList()) }
    val currentUser by remember { mutableStateOf(currentUser) }

    LaunchedEffect(Unit) {
        try {
            val current = revenueCatRepo.fetchCurrentOffering()
            offerings = current?.availablePackages
                ?.map { it.storeProduct }
                ?: emptyList()
        } catch (e: Throwable) {
//            onError(e)
        } finally {
            println(offerings)
//            isLoading = false
        }
    }

    val options = remember {
        PaywallOptions(dismissRequest = {
            onDismiss()
        }) {

            listener = object : PaywallListener {
                override fun onPurchaseCompleted(customerInfo: CustomerInfo, storeTransaction: StoreTransaction) {

                    val isPremium = customerInfo.entitlements["Qook Pro"]?.isActive == true

                    if (isPremium) {

                        println("🎉 프리미엄 유저가 되었습니다!")

                        onPurchaseComplete(currentUser)

                    }
                }

                override fun onRestoreCompleted(customerInfo: CustomerInfo) {

                    onPurchaseComplete(currentUser)

                }

                override fun onPurchaseCancelled() {

                }

                override fun onPurchaseError(error: PurchasesError) {

                }

                override fun onRestoreError(error: PurchasesError) {

                }

                // ... 기타 에러 처리
            }
            shouldDisplayDismissButton = true
        }
    }

    Paywall(options)
}