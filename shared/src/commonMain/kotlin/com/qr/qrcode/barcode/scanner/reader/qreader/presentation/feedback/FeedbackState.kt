package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.feedback

data class FeedbackState(
    val email: String = "",
    val comment: String = "",
    val isEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
)
