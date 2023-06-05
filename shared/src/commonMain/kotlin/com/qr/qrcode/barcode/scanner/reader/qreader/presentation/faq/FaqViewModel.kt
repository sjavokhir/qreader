package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.faq

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.FaqModel
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class FaqViewModel : KMMViewModel(), KoinComponent {

    private val stateData = MutableStateFlow(FaqState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        getFaq()
    }

    private fun getFaq() {
        stateData.update { it.copy(faq = faq) }
    }
}

private val faq = listOf(
    FaqModel(
        id = 1,
        question = "How does the QR scanner work?",
        answer = "The QR scanner in our app uses your device's camera to scan QR codes. Simply point the camera at the QR code, and the app will automatically detect and decode it."
    ),
    FaqModel(
        id = 2,
        question = "Can I create my own QR codes?",
        answer = "Yes, our app includes a QR code creator feature. You can enter the desired content, such as a URL, text, or contact information, and the app will generate a QR code for you."
    ),
    FaqModel(
        id = 3,
        question = "Is the app available for both Android and iOS?",
        answer = "Yes, our app is available for both Android and iOS devices. You can download it from the respective app stores."
    ),
    FaqModel(
        id = 4,
        question = "What are the benefits of the premium version?",
        answer = "The premium version of our app offers several additional features, such as ad-free scanning and creation, unlimited QR code scanning and generation, advanced customization options, and priority customer support."
    ),
    FaqModel(
        id = 5,
        question = "How can I upgrade to the premium version?",
        answer = "To upgrade to the premium version, open the app and navigate to the settings menu. Look for the \"Go Pro!\" option and follow the instructions to complete the upgrade process."
    ),
    FaqModel(
        id = 6,
        question = "Can I transfer my premium subscription to another device?",
        answer = "Yes, if you have an active premium subscription, you can use the same subscription on multiple devices. Simply log in to your account on the new device, and the premium features will be available to you."
    ),
    FaqModel(
        id = 7,
        question = "What happens if my premium subscription expires?",
        answer = "If your premium subscription expires, you will lose access to the premium features. However, you can still use the basic functionality of the app, including scanning and creating QR codes."
    ),
    FaqModel(
        id = 8,
        question = "How secure is the app in terms of scanning QR codes?",
        answer = "Our app prioritizes user security and privacy. We have implemented measures to ensure that the scanned QR codes do not pose any risks to your device or personal information. However, we always recommend exercising caution and scanning codes from trusted sources."
    ),
    FaqModel(
        id = 9,
        question = "Can I customize the appearance of the generated QR codes?",
        answer = "Yes, the premium version of our app allows you to customize the appearance of the generated QR codes. You can choose from different styles, colors, and even add a logo or image to make the QR code more visually appealing."
    ),
    FaqModel(
        id = 10,
        question = "How can I contact customer support?",
        answer = "If you have any further questions or need assistance, you can reach our customer support team by navigating to the \"Feedback\" section in the app. You'll find options to contact us via email or through our support website."
    ),
    FaqModel(
        id = 11,
        question = "Can I save scanned QR codes for future reference?",
        answer = "Yes, our app allows you to save the scanned QR codes to your device. You can access them later from the app's history or scan history section."
    ),
    FaqModel(
        id = 12,
        question = "Can I share the scanned QR codes with others?",
        answer = "Absolutely! After scanning a QR code, you can easily share it with others using various methods, such as messaging apps, email, or social media platforms."
    ),
    FaqModel(
        id = 13,
        question = "Does the app support scanning different types of QR codes?",
        answer = "Yes, our app supports scanning various types of QR codes, including URL links, text, contact information, calendar events, Wi-Fi network details, and more."
    ),
    FaqModel(
        id = 14,
        question = "Can I scan QR codes from images stored on my device?",
        answer = "Yes, our app provides an option to import images from your device's gallery or camera roll. You can select an image containing a QR code, and the app will scan it."
    ),
    FaqModel(
        id = 15,
        question = "Is there a limit to the number of QR codes I can create or scan?",
        answer = "The premium version of our app offers unlimited QR code creation and scanning. You can generate and scan as many QR codes as you need without any limitations."
    ),
    FaqModel(
        id = 16,
        question = "Does the app require an internet connection to scan QR codes?",
        answer = "The QR scanning functionality of our app works offline. However, for certain actions, such as opening web links or accessing online content embedded in the QR code, an internet connection is required."
    ),
    FaqModel(
        id = 17,
        question = "Can I export or print the QR codes I create?",
        answer = "Yes, our app allows you to export the generated QR codes as image files or PDFs. You can save them to your device or print them for various purposes, such as marketing materials or physical displays."
    ),
    FaqModel(
        id = 18,
        question = "How frequently are the app and its features updated?",
        answer = "We strive to provide regular updates to improve the app's performance, fix any issues, and introduce new features. Updates are typically released every few months, depending on user feedback and development priorities."
    ),
    FaqModel(
        id = 19,
        question = "What payment methods are accepted for the premium subscription?",
        answer = "We accept various payment methods, including credit cards, debit cards, and popular mobile payment options like Google Pay and Apple Pay. The available payment methods may vary depending on your app store and region."
    )
)