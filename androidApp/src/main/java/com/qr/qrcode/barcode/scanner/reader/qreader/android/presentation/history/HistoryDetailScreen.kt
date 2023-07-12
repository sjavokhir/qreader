package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.model.EditContentModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.model.QRCustomizeModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.model.toState
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.AddContentScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CustomizeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.qrCode.QRDetailContent
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.qrCode.QRCodeEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.qrCode.QRCodeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Destination
@Composable
fun HistoryDetailScreen(
    id: String,
    dateTime: String,
    isScanned: Boolean,
    generateMode: GenerateMode,
    encoded: String,
    decoded: String,
    customize: QRCustomizeModel,
    viewModel: QRCodeViewModel = viewModel(),
    navigator: DestinationsNavigator,
    resultCustomization: ResultRecipient<CustomizeScreenDestination, QRCustomizeModel>,
    resultAddContent: ResultRecipient<AddContentScreenDestination, EditContentModel>,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    var customizeModel by rememberSaveable { mutableStateOf(customize) }
    var encodedContent by rememberSaveable { mutableStateOf(encoded) }
    var decodedContent by rememberSaveable { mutableStateOf(decoded) }

    resultCustomization.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                customizeModel = result.value

                viewModel.onEvent(
                    QRCodeEvent.Insert(
                        id = id,
                        isScanned = isScanned,
                        mode = generateMode,
                        encoded = encodedContent,
                        decoded = decodedContent,
                        customize = result.value.toState()
                    )
                )
            }
        }
    }
    resultAddContent.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                encodedContent = result.value.encoded
                decodedContent = result.value.decoded

                viewModel.onEvent(
                    QRCodeEvent.Insert(
                        id = id,
                        isScanned = isScanned,
                        mode = generateMode,
                        encoded = encodedContent,
                        decoded = decodedContent,
                        customize = customizeModel.toState()
                    )
                )
            }
        }
    }

    QRBackground {
        QRDetailContent(
            dateTime = dateTime,
            generateMode = generateMode,
            encoded = encodedContent,
            decoded = decodedContent,
            customize = customizeModel,
            isEditable = true,
            isDeletable = true,
            isChromeCustomTabs = state.isChromeCustomTabsEnabled,
            onNavigate = navigator::navigate,
            onEdit = {
                navigator.navigate(
                    AddContentScreenDestination(
                        id = id,
                        generateMode = generateMode,
                        encoded = encodedContent,
                        isEditable = true
                    )
                )
            },
            onDelete = {
                viewModel.onEvent(QRCodeEvent.Delete(id))

                coroutineScope.launch {
                    delay(100L)
                    navigator.navigateUp()
                }
            }
        )
    }
}