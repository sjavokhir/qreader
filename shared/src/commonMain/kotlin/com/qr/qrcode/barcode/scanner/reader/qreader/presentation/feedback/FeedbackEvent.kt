package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.feedback

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.TopicModel

sealed class FeedbackEvent {
    data class ChangeEmail(val email: String) : FeedbackEvent()
    data class ChangeComment(val comment: String) : FeedbackEvent()
    data class Submit(val topic: TopicModel) : FeedbackEvent()
}