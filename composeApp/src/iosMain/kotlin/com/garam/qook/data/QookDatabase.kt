package com.garam.qook.data

import androidx.room.Room
import androidx.room.RoomDatabase
import com.garam.qook.data.local.QookDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun getDatabaseBuilder(): RoomDatabase.Builder<QookDatabase> {
    val dbFilePath = documentDirectory() + "/qook.db"
    return Room.databaseBuilder<QookDatabase>(
        name = dbFilePath
    )
}

private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}