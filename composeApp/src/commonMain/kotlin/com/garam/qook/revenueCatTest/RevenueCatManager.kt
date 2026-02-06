package com.garam.qook.revenueCatTest

import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.configure

object RevenueCatManager {
    fun init(apiKey: String, appUserId: String? = null) {
        // 로깅 설정
        Purchases.logLevel = LogLevel.DEBUG

        // RevenueCat 환경 설정
        Purchases.configure(apiKey = apiKey) {
            this.appUserId = appUserId
        }
    }
}