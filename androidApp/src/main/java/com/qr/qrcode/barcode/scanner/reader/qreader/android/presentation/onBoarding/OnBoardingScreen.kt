package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.onBoarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.HorizontalPagerIndicator
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QROutlinedButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ScannerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.onBoarding.OnBoardingEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.onBoarding.OnBoardingState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.onBoarding.OnBoardingViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.launch

@Destination
@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = viewModel(),
    navigator: DestinationsNavigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    QRBackground {
        OnBoardingScreenContent(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigate = navigator::navigate
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnBoardingScreenContent(
    state: OnBoardingState,
    onEvent: (OnBoardingEvent) -> Unit,
    onNavigate: (Direction) -> Unit
) {
    val strings = LocalStrings.current

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { 4 }

    val isStart = remember(pagerState.currentPage) { pagerState.currentPage == 3 }
    val resources = remember(pagerState.currentPage) {
        when (pagerState.currentPage + 1) {
            2 -> R.drawable.ic_onboarding_2 to (strings.onboardingTitle2 to strings.onboardingDescription2)
            3 -> R.drawable.ic_onboarding_3 to (strings.onboardingTitle3 to strings.onboardingDescription3)
            4 -> R.drawable.ic_onboarding_4 to (strings.onboardingTitle4 to strings.onboardingDescription4)
            else -> R.drawable.ic_onboarding_1 to (strings.onboardingTitle1 to strings.onboardingDescription1)
        }
    }

    LaunchedEffect(state.isStart) {
        if (state.isStart) {
            onNavigate(ScannerScreenDestination)
            onEvent(OnBoardingEvent.Idle)
        }
    }

    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = strings.appName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(id = resources.first),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(32.dp),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary)
        )

        Spacer(modifier = Modifier.weight(1f))

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = resources.second.first,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = resources.second.second,
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
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            QROutlinedButton(
                text = strings.skip,
                onClick = {
                    onEvent(OnBoardingEvent.Start)
                },
                modifier = Modifier.weight(1f)
            )

            QRFilledButton(
                text = if (isStart) strings.start else strings.next,
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