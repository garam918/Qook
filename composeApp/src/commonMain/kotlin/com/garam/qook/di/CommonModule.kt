package com.garam.qook.di

import com.garam.qook.MainViewModel
import com.garam.qook.auth.AuthRepository
import com.garam.qook.auth.AuthRepositoryProvider
import com.garam.qook.data.firebase.FirebaseDataSource
import com.garam.qook.data.firebase.FirebaseDataSourceProvider
import com.garam.qook.data.local.AnalysisDao
import com.garam.qook.data.local.GroceryDao
import com.garam.qook.data.repository.MainRepository
import com.garam.qook.data.repository.MainRepositoryImpl
import com.garam.qook.data.local.QookDatabase
import com.garam.qook.data.local.UserDao
import com.garam.qook.data.remote.ApiService
import com.garam.qook.data.repository.LoginRepository
import com.garam.qook.data.repository.LoginRepositoryImpl
import com.garam.qook.ui.groceryList.GroceryViewModel
import com.garam.qook.ui.home.HomeViewModel
import com.garam.qook.ui.login.LoginViewModel
import com.garam.qook.ui.result.AnalysisViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


fun commonModule() : Module = module {

    single<AnalysisDao> { get<QookDatabase>().analysisDao() }
    single<UserDao> { get<QookDatabase>().userDao() }
    single<GroceryDao> { get<QookDatabase>().groceryDao() }
    single<MainRepository> { get<MainRepositoryImpl>() }

    single<AuthRepository> { get<AuthRepositoryProvider>().get() }
    singleOf(::AuthRepositoryProvider)

    single<LoginRepository> { get<LoginRepositoryImpl>() }

    single { HttpClient { install(ContentNegotiation) { json() } } }
    single { ApiService(get()) }

    single<FirebaseDataSource> { get<FirebaseDataSourceProvider>().get() }

    singleOf(::MainRepositoryImpl) { bind<MainRepository>() }
    singleOf(::LoginRepositoryImpl) { bind<LoginRepository>() }
    singleOf(::FirebaseDataSourceProvider)

    factory { HomeViewModel(get()) }
    factory { MainViewModel(get()) }
    factory { LoginViewModel(get(), get()) }
    factory { AnalysisViewModel(get()) }
    factory { GroceryViewModel(get()) }



}