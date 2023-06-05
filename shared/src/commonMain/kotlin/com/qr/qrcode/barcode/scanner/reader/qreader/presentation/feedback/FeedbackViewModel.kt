package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.feedback

import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class FeedbackViewModel : BaseViewModel<FeedbackState, FeedbackEvent>(FeedbackState()),
    KoinComponent {

    override fun onEvent(event: FeedbackEvent) {
        when (event) {
            is FeedbackEvent.ChangeEmail -> onValuesChanged(email = event.email)
            is FeedbackEvent.ChangeComment -> onValuesChanged(comment = event.comment)
            is FeedbackEvent.Submit -> submitFeedback()
        }
    }

    private fun onValuesChanged(
        email: String? = null,
        comment: String? = null
    ) {
        stateData.update {
            val mEmail = email ?: it.email
            val mComment = comment ?: it.comment

            it.copy(
                email = mEmail,
                comment = mComment,
                isEnabled = mEmail.isNotEmpty() && mComment.isNotEmpty()
            )
        }
    }

    private fun submitFeedback() {
        stateData.update { it.copy(isSuccess = true) }
    }
}