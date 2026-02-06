package com.garam.qook.revenueCatTest

import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.ktx.awaitCustomerInfo
import com.revenuecat.purchases.kmp.ktx.awaitOfferings
import com.revenuecat.purchases.kmp.models.Offering

class RevenueCatRepository {
    /**
     * 현재 오퍼링 조회
     */
    suspend fun fetchCurrentOffering(): Offering? {
        val offerings = Purchases.sharedInstance.awaitOfferings()
        return offerings.current
    }

    /**
     * 구독 상태 체크
     */
    suspend fun hasActiveEntitlement(entId: String): Boolean {
        val customerInfo = Purchases.sharedInstance.awaitCustomerInfo()
        return customerInfo.entitlements[entId]?.isActive == true
    }
}