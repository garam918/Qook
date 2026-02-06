package com.garam.qook.data.local


import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


@Database(entities = [LocalRecipeAnalysis::class, LocalUserData::class, LocalGroceryData::class], version = 1, exportSchema = true)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(AnalysisTypeConverter::class)
abstract class QookDatabase : RoomDatabase(){

    abstract fun analysisDao() : AnalysisDao

    abstract fun groceryDao() : GroceryDao
    abstract fun userDao() : UserDao

}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<QookDatabase> {
    override fun initialize(): QookDatabase
}

fun getTodoDatabase(
    builder: RoomDatabase.Builder<QookDatabase>
) : QookDatabase = builder.setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()