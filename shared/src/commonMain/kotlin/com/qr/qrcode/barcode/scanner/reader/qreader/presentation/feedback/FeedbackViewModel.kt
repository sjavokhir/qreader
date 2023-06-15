package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.feedback

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.TopicModel
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class FeedbackViewModel : KMMViewModel(), KoinComponent {

    private val stateData = MutableStateFlow(viewModelScope, FeedbackState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: FeedbackEvent) {
        when (event) {
            is FeedbackEvent.SelectTopic -> onTopicSelected(event.topic)
            is FeedbackEvent.ChangeEmail -> onValuesChanged(email = event.email)
            is FeedbackEvent.ChangeComment -> onValuesChanged(comment = event.comment)
            FeedbackEvent.Submit -> submitFeedback()
        }
    }

    private fun onTopicSelected(topic: TopicModel) {
        stateData.update { it.copy(selectedTopic = topic) }
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