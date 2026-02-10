package com.garam.qook

import android.app.Application
import com.garam.qook.di.initKoin
import com.garam.qook.revenueCatTest.RevenueCatManager
import org.koin.android.ext.koin.androidContext

class QookApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            appDeclaration = { androidContext(this@QookApplication) },
        )

        val apiKey = BuildConfig.RevenueCat_API_KEY

        RevenueCatManager.init(
            apiKey = apiKey,
            appUserId = "app0f19328683",
        )
    }
}