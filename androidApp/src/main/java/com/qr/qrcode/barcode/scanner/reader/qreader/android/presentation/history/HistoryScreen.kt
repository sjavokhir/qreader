package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation.bottomNavigateTo
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CreatorScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.DirectionDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ScannerScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.launch

@Destination
@Composable
fun HistoryScreen(
    navigator: DestinationsNavigator
) {
    QRBackground {
        HistoryScreenContent(
            onBottomNavigateTo = navigator::bottomNavigateTo
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HistoryScreenContent(
    pageCount: Int = 2,
    onBottomNavigateTo: (Direction) -> Unit
) {
    val strings = LocalStrings.current

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { pageCount }

    val pages = remember {
        listOf(strings.scanned, strings.created)
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
            Column {
                QRTextField(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    value = "",
                    onValueChange = {},
                    placeholder = strings.searchQrCode
                )

                if (page == 1) {
                    HistoryNotFoundContent(false, onBottomNavigateTo)
                } else {
                    HistoryNotFoundContent(true, onBottomNavigateTo)
                }
            }
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
    isScannedHistory: Boolean,
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
                id = if (isScannedHistory) {
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
                text = if (isScannedHistory) {
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
            text = if (isScannedHistory) {
                strings.scan
            } else {
                strings.create
            },
            onClick = {
                if (isScannedHistory) {
                    onBottomNavigateTo(ScannerScreenDestination)
                } else {
                    onBottomNavigateTo(CreatorScreenDestination)
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}