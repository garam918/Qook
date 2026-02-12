package com.garam.qook.auth


expect class AuthRepositoryProvider() {
    fun get() : AuthRepository
}