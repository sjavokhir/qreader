package com.qr.qrcode.barcode.scanner.reader.qreader.android.camera.detector

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.qr.qrcode.barcode.scanner.reader.qreader.android.camera.core.VisionProcessorBase
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.openUrl
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.vibrate
import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.roundLast5
import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.tryCatch
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.business.BusinessContentState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.contact.ContactContentState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.email.EmailContentState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.location.LocationContentState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.phone.PhoneContentState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms.SmsContentState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia.SocialMediaContentState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia.detectSocialMedia
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.text.TextContentState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.website.WebsiteContentState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi.WifiContentState

class QRDetector(
    private val context: Context,
    private val isVibrateEnabled: Boolean,
    private val isOpenWebPagesEnabled: Boolean,
    private val isChromeCustomTabsEnabled: Boolean,
    private val onResult: (String, String, GenerateMode) -> Unit
) : VisionProcessorBase<MutableList<Barcode>>(context) {

    private var isCodeDetected: Boolean = false

    init {
        isCodeDetected = false
    }

    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions
            .Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .enableAllPotentialBarcodes()
            .build()
    )

    override fun stop() {
        super.stop()
        scanner.close()
    }

    override fun detectInImage(image: InputImage): Task<MutableList<Barcode>> {
        return scanner.process(image)
    }

    override fun onSuccess(results: MutableList<Barcode>) {
        if (!isCodeDetected) {
            tryCatch {
                filterResults(results)?.let { onBarcodeParser(it) }
            }
        }
    }

    override fun onFailure(t: Throwable) {}

    private fun filterResults(results: List<Barcode>): Barcode? {
        return results.filter { it.boundingBox != null && !it.rawValue.isNullOrEmpty() }
            .maxByOrNull { it.boundingBox!!.width() * it.boundingBox!!.height() }
    }

    private fun onBarcodeParser(code: Barcode) {
        tryCatch {
            val generateMode: GenerateMode
            val encoded: String
            val decoded: String
            val isNotBlank: Boolean
            var isUrl = false

            if (code.url != null) {
                isUrl = true

                val social = detectSocialMedia(code.url?.url.orEmpty())

                if (social != null) {
                    val content = SocialMediaContentState(
                        username = social.second,
                        mode = social.first,
                    )

                    generateMode = content.mode
                    encoded = content.encode()
                    decoded = content.decode()
                    isNotBlank = content.isNotBlank()
                } else {
                    val content = WebsiteContentState(code.url?.url.orEmpty())

                    generateMode = GenerateMode.Website
                    encoded = content.encode()
                    decoded = content.decode()
                    isNotBlank = content.isNotBlank()
                }
            } else if (code.sms != null) {
                val content = SmsContentState(
                    phone = code.sms?.phoneNumber.orEmpty(),
                    message = code.sms?.message.orEmpty()
                )

                generateMode = GenerateMode.Sms
                encoded = content.encode()
                decoded = content.decode()
                isNotBlank = content.isNotBlank()
            } else if (code.phone != null) {
                val content = PhoneContentState(code.phone?.number.orEmpty())

                generateMode = GenerateMode.PhoneNumber
                encoded = content.encode()
                decoded = content.decode()
                isNotBlank = content.isNotBlank()
            } else if (code.email != null) {
                val content = EmailContentState(
                    email = code.email?.address.orEmpty(),
                    subject = code.email?.subject.orEmpty(),
                    message = code.email?.body.orEmpty()
                )

                generateMode = GenerateMode.EmailAddress
                encoded = content.encode()
                decoded = content.decode()
                isNotBlank = content.isNotBlank()
            } else if (code.wifi != null) {
                val content = WifiContentState(
                    networkName = code.wifi?.ssid.orEmpty(),
                    password = code.wifi?.password.orEmpty(),
                    authentication = when (code.wifi?.encryptionType) {
                        1 -> WifiContentState.Authentication.OPEN
                        2 -> WifiContentState.Authentication.WPA_WPA2
                        else -> WifiContentState.Authentication.WEP
                    }
                )

                generateMode = GenerateMode.Wifi
                encoded = content.encode()
                decoded = content.decode()
                isNotBlank = content.isNotBlank()
            } else if (code.contactInfo != null) {
                if (
                    !code.contactInfo?.organization.isNullOrEmpty() ||
                    !code.contactInfo?.urls.isNullOrEmpty()
                ) {
                    val content = BusinessContentState(
                        name = code.contactInfo?.title.orEmpty(),
                        industry = code.contactInfo?.organization.orEmpty(),
                        phone = code.contactInfo?.phones?.firstOrNull()?.number.orEmpty(),
                        email = code.contactInfo?.emails?.firstOrNull()?.address.orEmpty(),
                        website = code.contactInfo?.urls?.firstOrNull().orEmpty(),
                        address = code.contactInfo?.addresses
                            ?.firstOrNull()?.addressLines
                            ?.joinToString(", ").orEmpty()
                    )

                    generateMode = GenerateMode.BusinessVCard
                    encoded = content.encode()
                    decoded = content.decode()
                    isNotBlank = content.isNotBlank()
                } else {
                    val content = ContactContentState(
                        name = code.contactInfo?.title.orEmpty(),
                        phone = code.contactInfo?.phones?.firstOrNull()?.number.orEmpty(),
                        email = code.contactInfo?.emails?.firstOrNull()?.address.orEmpty(),
                        address = code.contactInfo?.addresses
                            ?.firstOrNull()?.addressLines
                            ?.joinToString(", ").orEmpty()
                    )

                    generateMode = GenerateMode.ContactVCard
                    encoded = content.encode()
                    decoded = content.decode()
                    isNotBlank = content.isNotBlank()
                }
            } else if (code.calendarEvent != null) {
                val content = SmsContentState(
                    phone = code.sms?.phoneNumber.orEmpty(),
                    message = code.sms?.message.orEmpty()
                )

                generateMode = GenerateMode.CalendarEvent
                encoded = content.encode()
                decoded = content.decode()
                isNotBlank = content.isNotBlank()
            } else if (code.geoPoint != null) {
                val content = LocationContentState(
                    latitude = code.geoPoint?.lat?.roundLast5()?.toString().orEmpty(),
                    longitude = code.geoPoint?.lng?.roundLast5()?.toString().orEmpty()
                )

                generateMode = GenerateMode.Location
                encoded = content.encode()
                decoded = content.decode()
                isNotBlank = content.isNotBlank()
            } else {
                val content = TextContentState(code.rawValue.orEmpty())

                generateMode = GenerateMode.Text
                encoded = content.encode()
                decoded = content.decode()
                isNotBlank = content.isNotBlank()
            }

            if (isNotBlank) {
                isCodeDetected = true

                if (isVibrateEnabled) {
                    context.vibrate()
                }

                if (isOpenWebPagesEnabled && isUrl) {
                    context.openUrl(decoded, isChromeCustomTabsEnabled)
                }

                onResult(encoded, decoded, generateMode)
            }
        }
    }
}