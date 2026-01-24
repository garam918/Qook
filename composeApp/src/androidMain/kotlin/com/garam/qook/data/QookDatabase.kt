package com.garam.qook.com.garam.qook.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.garam.qook.data.QookDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<QookDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("qook.db")
    return Room.databaseBuilder<QookDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}