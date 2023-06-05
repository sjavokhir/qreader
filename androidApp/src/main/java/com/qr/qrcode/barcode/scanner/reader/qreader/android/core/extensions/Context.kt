package com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.qr.qrcode.barcode.scanner.reader.qreader.core.helpers.tryCatch

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