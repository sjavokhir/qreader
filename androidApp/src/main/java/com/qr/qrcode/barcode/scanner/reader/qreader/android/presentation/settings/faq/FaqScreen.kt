package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.settings.faq

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.FaqModel
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.faq.FaqState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.faq.FaqViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun FaqScreen(
    viewModel: FaqViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    QRBackground {
        FaqScreenContent(state = state)
    }
}

@Composable
private fun FaqScreenContent(state: FaqState) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        items(state.faq) { faq ->
            FaqItem(faq)
        }
    }
}

@Composable
private fun FaqItem(faq: FaqModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = faq.question,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = faq.answer,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline
        )
    }
}
