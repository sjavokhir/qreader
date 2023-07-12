package com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.toCalendarTimestamp
import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.tryCatch
import com.qr.qrcode.barcode.scanner.reader.qreader.core.helpers.StringRes
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi.WifiContentState
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

fun Context.copyToClipboard(text: String) {
    tryCatch {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(StringRes.copiedText, text)
        clipboard.setPrimaryClip(clip)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            toast(StringRes.copiedToClipboard)
        }
    }
}

fun Context.dial(phone: String) {
    tryCatch {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        startActivity(intent)
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

fun Context.openUrl(
    url: String,
    isChromeCustomTabs: Boolean = false
) {
    tryCatch {
        val uri = if (url.startsWith("http://") || url.startsWith("https://")) {
            Uri.parse(url)
        } else {
            Uri.parse("https://$url")
        }

        if (isChromeCustomTabs) {
            val intent = CustomTabsIntent.Builder().build()
            intent.launchUrl(this, uri)
        } else {
            val intent = Intent(Intent.ACTION_VIEW).apply { data = uri }
            startActivity(intent)
        }
    }
}

fun Context.searchGoogle(
    query: String,
    isChromeCustomTabs: Boolean = false
) {
    openUrl(
        "https://www.google.com/search?q=$query",
        isChromeCustomTabs
    )
}

fun Context.sendMail(
    email: String,
    subject: String = "",
    message: String = ""
) {
    tryCatch {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        startActivity(Intent.createChooser(intent, StringRes.sendEmail))
    }
}

fun Context.sendSms(phone: String, message: String) {
    tryCatch {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:$phone")
            putExtra("sms_body", message)
        }
        startActivity(intent)
    }
}

fun Context.addContact(
    phone: String,
    name: String = "",
    company: String = "",
    job: String = "",
    email: String = "",
    address: String = "",
) {
    tryCatch {
        val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
            type = ContactsContract.RawContacts.CONTENT_TYPE
            putExtra(ContactsContract.Intents.Insert.NAME, name)
            putExtra(ContactsContract.Intents.Insert.PHONE, phone)

            if (company.isNotEmpty()) {
                putExtra(ContactsContract.Intents.Insert.COMPANY, company)
            }
            if (job.isNotEmpty()) {
                putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job)
            }
            if (email.isNotEmpty()) {
                putExtra(ContactsContract.Intents.Insert.EMAIL, email)
            }
            if (address.isNotEmpty()) {
                putExtra(ContactsContract.Intents.Insert.NOTES, address)
            }
        }
        startActivity(intent)
    }
}

fun Context.showLocation(latitude: String, longitude: String) {
    tryCatch {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("geo:$latitude,$longitude")
        }
        startActivity(intent)
    }
}

fun Context.showAddress(address: String) {
    tryCatch {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("geo:0,0?q=$address")
        }
        startActivity(intent)
    }
}

@Suppress("DEPRECATION")
fun Context.connectToWifi(
    networkName: String,
    password: String,
    authentication: WifiContentState.Authentication?,
    isHidden: Boolean
) {
    tryCatch {
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val suggestion = WifiNetworkSuggestion.Builder()
                .setSsid(networkName)
                .setIsHiddenSsid(isHidden)

            when (authentication) {
                WifiContentState.Authentication.WPA_WPA2 -> {
                    suggestion.setWpa2Passphrase(password)
                }

                WifiContentState.Authentication.WEP -> {
                    suggestion.setWpa2Passphrase(password)
                }

                else -> {}
            }

            val suggestions = listOf(suggestion.build())
            wifiManager.addNetworkSuggestions(suggestions)

            val intentFilter =
                IntentFilter(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)

            val broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (!intent.action.equals(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)) {
                        return
                    }
                }
            }
            registerReceiver(broadcastReceiver, intentFilter)
        } else {
            val wifiConfig = WifiConfiguration()
            wifiConfig.SSID = "\"$networkName\""
            wifiConfig.status = WifiConfiguration.Status.ENABLED
            wifiConfig.hiddenSSID = isHidden

            when (authentication) {
                WifiContentState.Authentication.WPA_WPA2 -> {
                    wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
                    wifiConfig.preSharedKey = "\"$password\""
                }

                WifiContentState.Authentication.WEP -> {
                    wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                    wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
                    wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
                    wifiConfig.wepKeys[0] = "\"$password\""
                    wifiConfig.wepTxKeyIndex = 0
                }

                else -> {
                    wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                }
            }

            if (!wifiManager.isWifiEnabled) {
                wifiManager.isWifiEnabled = true
            }

            val networkId = wifiManager.addNetwork(wifiConfig)
            wifiManager.disconnect()
            wifiManager.enableNetwork(networkId, true)
            wifiManager.reconnect()
        }
    }
}

fun Context.addToCalendar(
    name: String,
    location: String,
    description: String,
    isAllDay: Boolean,
    startMillis: Long?,
    endMillis: Long?
) {
    tryCatch {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI

            putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, isAllDay)

            if (startMillis != null && startMillis != 0L) {
                putExtra(
                    CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                    startMillis.toCalendarTimestamp()
                )
            }

            if (endMillis != null && endMillis != 0L) {
                putExtra(
                    CalendarContract.EXTRA_EVENT_END_TIME,
                    endMillis.toCalendarTimestamp()
                )
            }

            putExtra(CalendarContract.Events.TITLE, name)

            if (description.isNotEmpty()) {
                putExtra(CalendarContract.Events.DESCRIPTION, description)
            }

            if (location.isNotEmpty()) {
                putExtra(CalendarContract.Events.EVENT_LOCATION, location)
            }
        }
        startActivity(intent)
    }
}