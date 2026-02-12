package com.garam.qook.data.firebase

import com.garam.qook.com.garam.qook.data.firebase.FirebaseDataSourceImpl

actual class FirebaseDataSourceProvider actual constructor() {
    actual fun get(): FirebaseDataSource = FirebaseDataSourceImpl()
}