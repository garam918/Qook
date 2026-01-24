package com.garam.qook

import android.app.Application
import com.garam.qook.di.initKoin
import org.koin.android.ext.koin.androidContext

//import com.garam.qook.revenueCatTest.configureRevenueCat

class QookApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            appDeclaration = { androidContext(this@QookApplication) },
        )
//        configureRevenueCat()
    }
}