package com.garam.qook.util

import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

@OptIn(ExperimentalForeignApi::class)
actual fun shareText(text: String) {
    dispatch_async(dispatch_get_main_queue()) {
        val controller = UIActivityViewController(
            activityItems = listOf(text),
            applicationActivities = null
        )

        val rootController =
            UIApplication.sharedApplication.keyWindow?.rootViewController
                ?: return@dispatch_async

        rootController.presentViewController(
            controller,
            animated = true,
            completion = null
        )
    }
}