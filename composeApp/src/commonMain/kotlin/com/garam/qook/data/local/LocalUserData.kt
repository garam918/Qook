package com.garam.qook.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.serialization.Serializable
import kotlin.time.Clock
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
    val usageCount: Int = 3,
    val lastUseDate : String = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
)
