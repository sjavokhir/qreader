package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.qrCode

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.model.QRCustomizeModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.model.toState
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CustomizeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.currentTimestamp
import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.timestampToDateTime
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.qrCode.QRCodeEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.qrCode.QRCodeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@Destination
@Composable
fun QRCodeScreen(
    id: String,
    isScanned: Boolean,
    generateMode: GenerateMode,
    encoded: String,
    decoded: String,
    viewModel: QRCodeViewModel = viewModel(),
    navigator: DestinationsNavigator,
    resultCustomization: ResultRecipient<CustomizeScreenDestination, QRCustomizeModel>
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val dateTime = remember {
        currentTimestamp().timestampToDateTime().defaultDateTime
    }

    var customizeModel by rememberSaveable { mutableStateOf(QRCustomizeModel()) }

    resultCustomization.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                customizeModel = result.value
            }
        }
    }

    LaunchedEffect(id, encoded, customizeModel) {
        viewModel.onEvent(
            QRCodeEvent.Insert(
                id = id,
                isScanned = isScanned,
                mode = generateMode,
                encoded = encoded,
                decoded = decoded,
                customize = customizeModel.toState()
            )
        )
    }

    QRBackground {
        QRDetailContent(
            dateTime = dateTime,
            generateMode = generateMode,
            encoded = encoded,
            decoded = decoded,
            customize = customizeModel,
            isChromeCustomTabs = state.isChromeCustomTabsEnabled,
            onNavigate = navigator::navigate
        )
    }
}