package com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.tryCatch
import com.qr.qrcode.barcode.scanner.reader.qreader.core.helpers.Constants
import kotlin.system.exitProcess

fun Context.drawableId(name: String): Int? {
    return try {
        resources.getIdentifier(
            name,
            "drawable",
            packageName
        )
    } catch (t: Throwable) {
        null
    }
}

fun Context.getActivity(): Activity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

fun Context.restartApp() {
    getActivity()?.let { activity ->
        val intent = activity.intent
        activity.finish()
        activity.startActivity(intent)
    } ?: {
        exitProcess(0)
    }
}

fun Context.toast(message: String?) {
    if (!message.isNullOrEmpty()) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context.gotoUrl(url: String) {
    tryCatch {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}

fun Context.shareText(text: String) {
    tryCatch {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, null))
    }
}

fun Context.sendMail() {
    tryCatch {
        val intent = Intent(
            Intent.ACTION_SENDTO,
            Uri.fromParts("mailto", Constants.EMAIL, null)
        )
        intent.putExtra(Intent.EXTRA_SUBJECT, "Simple Vocabulary")
        intent.putExtra(Intent.EXTRA_TEXT, "Contact developer")
        startActivity(Intent.createChooser(intent, "Send mail"))
    }
}