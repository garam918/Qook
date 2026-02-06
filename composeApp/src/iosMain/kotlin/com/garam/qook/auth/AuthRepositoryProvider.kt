package com.garam.qook.auth

import com.garam.qook.data.firebase.FirebaseDataSource
import com.garam.qook.data.local.UserDao

actual class AuthRepositoryProvider {
    actual fun get(): AuthRepository = AuthRepositoryImpl()
}