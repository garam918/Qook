package com.garam.qook.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {

    @Upsert
    suspend fun saveUserData(localUserData: LocalUserData)

    @Query("DELETE FROM user WHERE uid = :uid")
    suspend fun deleteUserData(uid : String)
}