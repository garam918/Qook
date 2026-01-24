package com.garam.qook.di

import com.garam.qook.MainViewModel
import com.garam.qook.auth.AuthRepository
import com.garam.qook.auth.AuthRepositoryProvider
import com.garam.qook.data.AnalysisDao
import com.garam.qook.data.MainRepository
import com.garam.qook.data.MainRepositoryImpl
import com.garam.qook.data.QookDatabase
import com.garam.qook.ui.home.HomeViewModel
import com.garam.qook.ui.login.LoginViewModel
import com.garam.qook.ui.result.AnalysisViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import kotlin.text.get


fun commonModule() : Module = module {

    single<AnalysisDao> { get<QookDatabase>().analysisDao() }
    single<MainRepository> { get<MainRepositoryImpl>() }

    single<AuthRepository> { get<AuthRepositoryProvider>().get() }
    singleOf(::AuthRepositoryProvider)


    factory { HomeViewModel(get()) }
    factory { MainViewModel(get()) }
    factory { LoginViewModel(get()) }
    factory { AnalysisViewModel() }

    singleOf(::MainRepositoryImpl)

}