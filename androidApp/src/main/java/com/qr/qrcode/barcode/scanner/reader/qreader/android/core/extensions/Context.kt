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
import android.provider.ContactsContract
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.log
import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.tryCatch
import com.qr.qrcode.barcode.scanner.reader.qreader.core.helpers.StringRes
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
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(StringRes.copiedText, text)
    clipboard.setPrimaryClip(clip)

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        toast(StringRes.copiedToClipboard)
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

        isChromeCustomTabs.log()

        if (isChromeCustomTabs) {
            val intent = CustomTabsIntent.Builder().build()
            intent.launchUrl(this, uri)
        } else {
            val intent = Intent(Intent.ACTION_VIEW).apply { data = uri }
            startActivity(intent)
        }
    }
}

fun Context.searchText(
    text: String,
    isChromeCustomTabs: Boolean = false
) {
    openUrl(
        "https://www.google.com/search?q=$text",
        isChromeCustomTabs
    )
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
            startActivity(Intent.createChooser(intent, StringRes.sendEmail))
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

fun Context.showLocation(uriString: String) {
    tryCatch {
        if (uriString.startsWith("geo:")) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(uriString)
            }
            startActivity(intent)
        }
    }
}

@Suppress("DEPRECATION")
fun Context.connectToWifi(uriString: String) {
    tryCatch {
        val wifiRegex = Regex("""WIFI:S:(.*?);T:(.*?);P:(.*?);H:(.*?);""")
        val matchResult = wifiRegex.find(uriString)

        matchResult?.groupValues?.let { groups ->
            val networkName = groups[1]
            val authentication = groups[2]
            val password = groups[3]
            val isHidden = groups[4].toBooleanStrictOrNull() ?: false

            val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val suggestion = WifiNetworkSuggestion.Builder()
                    .setSsid(networkName)
                    .setIsHiddenSsid(isHidden)

                when (authentication) {
                    "WPA" -> {
                        suggestion.setWpa2Passphrase(password)
                    }

                    "WEP" -> {
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
                    "WPA" -> {
                        wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
                        wifiConfig.preSharedKey = "\"$password\""
                    }

                    "WEP" -> {
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
}

fun Context.copyWifiNetworkName(uriString: String) {
    tryCatch {
        val wifiRegex = Regex("""WIFI:S:(.*?);T:(.*?);P:(.*?);H:(.*?);""")
        val matchResult = wifiRegex.find(uriString)

        matchResult?.groupValues?.let { groups ->
            copyToClipboard(groups[1])
        }
    }
}

fun Context.copyWifiPassword(uriString: String) {
    tryCatch {
        val wifiRegex = Regex("""WIFI:S:(.*?);T:(.*?);P:(.*?);H:(.*?);""")
        val matchResult = wifiRegex.find(uriString)

        matchResult?.groupValues?.let { groups ->
            copyToClipboard(groups[3])
        }
    }
}