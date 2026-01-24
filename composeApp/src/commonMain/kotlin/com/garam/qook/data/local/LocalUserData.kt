package com.garam.qook.data.local

import androidx.room.PrimaryKey

data class LocalUserData(
    val uid : String,
    val email: String?,
    val loginType : String
)
