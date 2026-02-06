package com.garam.qook.revenueCatTest

import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.StoreProduct
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object PurchaseManager {

    // 현재 구매 가능한 상품 가져오기
    suspend fun fetchOfferings(): List<StoreProduct> = suspendCoroutine { continuation ->
        Purchases.sharedInstance.getOfferings(
            onError = { error ->
                println("RevenueCat Error: ${error.message}")
                continuation.resume(emptyList())
            },
            onSuccess = { offerings ->
                // 'current' offering에 있는 availablePackages에서 product만 추출
                val products = offerings.current?.availablePackages?.map { it.storeProduct }
                    ?: emptyList()
                continuation.resume(products)
            }
        )
    }

    // 구매 실행하기
    suspend fun purchaseProduct(product: StoreProduct): Boolean = suspendCoroutine { continuation ->
        // StoreProduct를 다시 Package로 찾아서 구매해야 하는 경우가 많지만,
        // KMP 최신 버전은 StoreProduct 직접 구매나 Package 구매를 지원합니다.
        // 여기서는 가장 확실한 방법인 purchaseWith(package) 로직을 위해
        // Offerings에서 가져온 Package 객체를 그대로 쓰는 것을 추천합니다.

        // *참고: 이 예제 단순화를 위해 로직만 보여드립니다.
        // 실제로는 UI에서 Package 객체를 넘겨받는 것이 좋습니다.

        // (간소화된 구매 호출 예시)
        Purchases.sharedInstance.purchase(
            storeProduct = product, // 혹은 package = packageObj
            onError = { error, userCancelled ->
                println("Purchase Failed: ${error.message}")
                continuation.resume(false)
            },
            onSuccess = { storeTransaction, customerInfo ->
                println("Purchase Success!")
                continuation.resume(true)
            }
        )
    }
}