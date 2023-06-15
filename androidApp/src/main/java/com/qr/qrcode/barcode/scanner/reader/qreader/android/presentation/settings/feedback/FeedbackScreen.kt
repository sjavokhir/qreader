package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.settings.feedback

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRDropdown
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.TopicModel
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.feedback.FeedbackEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.feedback.FeedbackState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.feedback.FeedbackViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun FeedbackScreen(
    viewModel: FeedbackViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    QRBackground {
        FeedbackScreenContent(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun FeedbackScreenContent(
    state: FeedbackState,
    onEvent: (FeedbackEvent) -> Unit
) {
    if (state.isSuccess) {
        ThankYouContent()
    } else {
        FeedbackContent(state, onEvent)
    }
}

@Composable
private fun FeedbackContent(
    state: FeedbackState,
    onEvent: (FeedbackEvent) -> Unit
) {
    val context = LocalContext.current

    val options = remember {
        listOf(
            TopicModel(1, context.getString(R.string.feedback_topic_1)),
            TopicModel(2, context.getString(R.string.feedback_topic_2)),
            TopicModel(3, context.getString(R.string.feedback_topic_3)),
            TopicModel(4, context.getString(R.string.feedback_topic_4)),
            TopicModel(5, context.getString(R.string.feedback_topic_5)),
            TopicModel(6, context.getString(R.string.feedback_topic_6))
        )
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        item { HelpUsImproveContent() }
        item {
            QRDropdown(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                hint = stringResource(id = R.string.select_topic),
                selectedOption = selectedOption,
                onSelectedOption = {
                    selectedOption = it
                    expanded = false
                },
                options = options
            )
        }
        item {
            QRTextField(
                value = state.email,
                onValueChange = { onEvent(FeedbackEvent.ChangeEmail(it)) },
                placeholder = stringResource(id = R.string.email_address),
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        }
        item {
            QRTextField(
                modifier = Modifier.defaultMinSize(minHeight = 120.dp),
                value = state.comment,
                onValueChange = { onEvent(FeedbackEvent.ChangeComment(it)) },
                placeholder = stringResource(id = R.string.write_your_comments)
            )
        }
        item {
            QRFilledButton(
                text = stringResource(id = R.string.action_submit),
                enabled = state.isEnabled && !state.isLoading,
                onClick = { onEvent(FeedbackEvent.Submit(selectedOption)) }
            )
        }
    }
}

@Composable
private fun HelpUsImproveContent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_feedback_illustration),
            contentDescription = null
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.help_us_improve),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(id = R.string.please_select_topic),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ThankYouContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_thank_you_illustration),
            contentDescription = null
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.thank_you),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(id = R.string.thank_you_description),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}