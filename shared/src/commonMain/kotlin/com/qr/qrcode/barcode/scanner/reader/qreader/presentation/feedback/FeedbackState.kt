package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.feedback

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.TopicModel

data class FeedbackState(
    val selectedTopic: TopicModel = TopicModel(1, "Failed to scan"),
    val email: String = "",
    val comment: String = "",
    val isEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
) {
    val topics: List<TopicModel>
        get() = listOf(
            TopicModel(1, "Failed to scan"),
            TopicModel(2, "Suggest a feature"),
            TopicModel(3, "Report a bug"),
            TopicModel(4, "Cancel subscription"),
            TopicModel(5, "Request a refund"),
            TopicModel(6, "Other")
        )
}
