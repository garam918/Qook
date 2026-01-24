package com.garam.qook.di

import com.garam.qook.data.QookDatabase
import com.garam.qook.data.getDatabaseBuilder
import com.garam.qook.data.getTodoDatabase
import org.koin.core.module.Module
import org.koin.dsl.module


actual fun platformModule(): Module = module {
    single<QookDatabase> {
        val builder = getDatabaseBuilder()
        getTodoDatabase(builder)
    }
}