package com.garam.qook.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Serializable
@Entity(
    tableName = "user"
)
data class LocalUserData(
    @PrimaryKey val uid : String = "",
    val email: String? = "",
    val loginType : String = "",
    val paid : Boolean = false,
    val usageCount: Int = 0,
    val lastUseDate : String = ""
)
