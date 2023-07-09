package com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.tryCatch
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

fun Context.shareText(text: String) {
    tryCatch {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(intent, null))
    }
}

fun Context.openUrl(url: String) {
    tryCatch {
        val intent = Intent(Intent.ACTION_VIEW)

        if (url.startsWith("http://") || url.startsWith("https://")) {
            intent.data = Uri.parse(url)
        } else {
            intent.data = Uri.parse("https://$url")
        }

        startActivity(intent)
    }
}

fun Context.searchText(text: String) {
    openUrl("https://www.google.com/search?q=$text")
}

fun Context.sendMail(uriString: String) {
    tryCatch {
        if (uriString.startsWith("mailto:")) {
            val parts = uriString.split("?subject=", "?body=")
            val email = parts[0].removePrefix("mailto:")
            val subject = parts[1]
            val message = parts[2]

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)
            }
            startActivity(Intent.createChooser(intent, "Send email"))
        }
    }
}

fun Context.sendSms(uriString: String) {
    tryCatch {
        if (uriString.startsWith("smsto:")) {
            val parts = uriString.split("?body=")
            val phone = parts[0].removePrefix("smsto:")
            val message = parts[1]

            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:$phone")
                putExtra("sms_body", message)
            }
            startActivity(intent)
        }
    }
}

fun Context.addContact(uriString: String) {
    tryCatch {
        if (uriString.startsWith("smsto:")) {
            val parts = uriString.split("?body=")
            val phone = parts[0].removePrefix("smsto:")

            val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
                type = ContactsContract.RawContacts.CONTENT_TYPE
                putExtra(ContactsContract.Intents.Insert.PHONE, phone)
            }
            startActivity(intent)
        } else if (uriString.startsWith("tel:")) {
            val recipient = uriString.substringAfter("tel:")

            val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
                type = ContactsContract.RawContacts.CONTENT_TYPE
                putExtra(ContactsContract.Intents.Insert.PHONE, recipient)
            }
            startActivity(intent)
        }
    }
}