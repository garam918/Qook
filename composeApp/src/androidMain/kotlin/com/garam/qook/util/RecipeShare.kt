package com.garam.qook.util

import android.content.Context
import android.content.Intent
import com.garam.qook.AndroidContextProvider

actual fun shareText(text: String) {
    val activity = AndroidContextProvider.activity ?: return

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }

    activity.startActivity(
        Intent.createChooser(intent, null)
    )

}