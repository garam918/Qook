package com.garam.qook.auth

actual class AuthRepositoryProvider {
    actual fun get(): AuthRepository = AuthRepositoryImpl()
}