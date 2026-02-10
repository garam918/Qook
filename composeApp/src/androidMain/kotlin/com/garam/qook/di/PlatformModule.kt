package com.garam.qook.di

import com.garam.qook.com.garam.qook.data.getDatabaseBuilder
import com.garam.qook.data.local.QookDatabase
import com.garam.qook.data.local.getTodoDatabase
import org.koin.core.module.Module
import org.koin.dsl.module


actual fun platformModule(): Module = module {
    single<QookDatabase> {
        val builder = getDatabaseBuilder(context = get())
        getTodoDatabase(builder)
    }
}