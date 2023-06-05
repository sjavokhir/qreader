package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.onBoarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.HorizontalPagerIndicator
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QROutlinedButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation.NavigationTree
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.onBoarding.OnBoardingEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.onBoarding.OnBoardingState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    state: OnBoardingState,
    onEvent: (OnBoardingEvent) -> Unit,
    onNavigate: (NavigationTree) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val isStart = remember(pagerState.currentPage) {
        pagerState.currentPage == 3
    }
    val titleAndDescription = remember(pagerState.currentPage) {
        when (pagerState.currentPage + 1) {
            2 -> R.string.onboarding_title_2 to R.string.onboarding_description_2
            3 -> R.string.onboarding_title_3 to R.string.onboarding_description_3
            4 -> R.string.onboarding_title_4 to R.string.onboarding_description_4
            else -> R.string.onboarding_title_1 to R.string.onboarding_description_1
        }
    }

    LaunchedEffect(state.isStart) {
        if (state.isStart) {
            onNavigate(NavigationTree.Scanner)
            onEvent(OnBoardingEvent.Idle)
        }
    }

    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.size(240.dp),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.weight(1f))

        HorizontalPager(
            pageCount = 4,
            state = pagerState,
            userScrollEnabled = false
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(id = titleAndDescription.first),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(id = titleAndDescription.second),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = 4
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            QROutlinedButton(
                text = stringResource(id = R.string.action_skip),
                onClick = {
                    onEvent(OnBoardingEvent.Start)
                },
                modifier = Modifier.weight(1f)
            )

            QRFilledButton(
                text = if (isStart) {
                    stringResource(id = R.string.action_start)
                } else {
                    stringResource(id = R.string.action_next)
                },
                onClick = {
                    if (isStart) {
                        onEvent(OnBoardingEvent.Start)
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}