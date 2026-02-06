package com.garam.qook.data.firebase

actual class FirebaseDataSourceProvider {
    actual fun get() : FirebaseDataSource = FirebaseDataSourceImpl()
}