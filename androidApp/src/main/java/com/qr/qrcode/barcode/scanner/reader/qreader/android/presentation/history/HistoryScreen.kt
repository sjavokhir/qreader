package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.history

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.drawableId
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.model.toModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.DividerContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation.bottomNavigateTo
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CreatorScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.DirectionDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.QRCodeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ScannerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.toDateTime
import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.toDefaultDateTime
import com.qr.qrcode.barcode.scanner.reader.qreader.data.database.entity.HistoryEntity
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.toGenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.history.HistoryEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.history.HistoryState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.history.HistoryViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.launch

@Destination
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel(),
    navigator: DestinationsNavigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    QRBackground {
        HistoryScreenContent(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigate = navigator::navigate,
            onBottomNavigateTo = navigator::bottomNavigateTo
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HistoryScreenContent(
    state: HistoryState,
    onEvent: (HistoryEvent) -> Unit,
    onNavigate: (Direction) -> Unit,
    onBottomNavigateTo: (Direction) -> Unit
) {
    val strings = LocalStrings.current
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState { 2 }
    val pages = remember { listOf(strings.scanned, strings.created) }

    LaunchedEffect(pagerState.currentPage) {
        onEvent(HistoryEvent.PageChanged(pagerState.currentPage))
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .padding(3.dp)
        ) {
            pages.forEachIndexed { index, title ->
                TabContent(
                    title = title,
                    isCurrentPage = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(
                                page = index,
                                pageOffsetFraction = pagerState.currentPageOffsetFraction
                            )
                        }
                    }
                )
            }
        }

        HorizontalPager(state = pagerState) { page ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (page == 0) {
                    QRTextField(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        value = state.scannedQuery,
                        onValueChange = {
                            onEvent(HistoryEvent.QueryChanged(true, it))
                        },
                        placeholder = strings.searchQrCode
                    )

                    if (state.scannedHistory.isEmpty()) {
                        HistoryNotFoundContent(true, onBottomNavigateTo)
                    } else {
                        HistoryContent(true, state.scannedHistory, onNavigate)
                    }
                } else {
                    QRTextField(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        value = state.createdQuery,
                        onValueChange = {
                            onEvent(HistoryEvent.QueryChanged(false, it))
                        },
                        placeholder = strings.searchQrCode
                    )

                    if (state.createdHistory.isEmpty()) {
                        HistoryNotFoundContent(false, onBottomNavigateTo)
                    } else {
                        HistoryContent(false, state.createdHistory, onNavigate)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryContent(
    isScanned: Boolean,
    history: List<HistoryEntity>,
    onNavigate: (Direction) -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(history) { index, entity ->
            HistoryContentItem(
                context = context,
                entity = entity,
                mode = entity.generateMode.toGenerateMode(),
                isLastItem = index == history.lastIndex,
                onClick = {
                    onNavigate(
                        QRCodeScreenDestination(
                            id = entity.id,
                            dateTime = entity.timestamp.toDefaultDateTime(),
                            isScanned = isScanned,
                            generateMode = entity.generateMode.toGenerateMode(),
                            encoded = entity.encoded,
                            decoded = entity.decoded,
                            customize = entity.toModel(),
                            isEditable = true,
                            isDeletable = true
                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun HistoryContentItem(
    context: Context,
    entity: HistoryEntity,
    mode: GenerateMode,
    isLastItem: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle(onClick = onClick)
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            context.drawableId(mode.icon)?.let { icon ->
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = mode.title,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = mode.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = entity.decoded,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Text(
                text = entity.timestamp.toDateTime().dateTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!isLastItem) {
            DividerContent()
        }
    }
}

@Composable
private fun RowScope.TabContent(
    title: String,
    isCurrentPage: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = title,
        modifier = Modifier
            .weight(1f)
            .clip(MaterialTheme.shapes.medium)
            .background(
                if (isCurrentPage) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                }
            )
            .clickableSingle(
                onClick = onClick,
                hasIndication = false
            )
            .padding(vertical = 8.dp),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = if (isCurrentPage) {
            FontWeight.Medium
        } else {
            FontWeight.Normal
        },
        color = if (isCurrentPage) {
            MaterialTheme.colorScheme.background
        } else {
            MaterialTheme.colorScheme.primary
        },
        textAlign = TextAlign.Center
    )
}

@Composable
private fun HistoryNotFoundContent(
    isScanned: Boolean,
    onBottomNavigateTo: (DirectionDestination) -> Unit
) {
    val strings = LocalStrings.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(
                id = if (isScanned) {
                    R.drawable.ic_history_scanned_empty_illustration
                } else {
                    R.drawable.ic_history_created_empty_illustration
                }
            ),
            contentDescription = null
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = strings.noContentFound,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Text(
                text = if (isScanned) {
                    strings.clickScanButton
                } else {
                    strings.clickCreateButton
                },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center
            )
        }

        QRFilledButton(
            text = if (isScanned) {
                strings.scan
            } else {
                strings.create
            },
            onClick = {
                if (isScanned) {
                    onBottomNavigateTo(ScannerScreenDestination)
                } else {
                    onBottomNavigateTo(CreatorScreenDestination)
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}