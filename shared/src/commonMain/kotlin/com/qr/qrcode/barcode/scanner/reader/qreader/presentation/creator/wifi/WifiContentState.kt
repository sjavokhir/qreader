package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.TopicModel

data class WifiContentState(
    val networkName: String = "",
    val password: String = "",
    val selectedType: TopicModel = TopicModel(2, "WPA"),
    val isEnabled: Boolean = false,
    val generateText: String = ""
) {
    val encryptionTypes: List<TopicModel>
        get() = listOf(
            TopicModel(1, "WEP"),
            TopicModel(2, "WPA"),
            TopicModel(3, "WPA-EAP"),
            TopicModel(4, "WPA/WPA2")
        )
}
