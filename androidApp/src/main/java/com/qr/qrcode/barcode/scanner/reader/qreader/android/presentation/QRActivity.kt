package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.theme.QRTheme
import com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation.QRApp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.premium.PremiumViewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.LanguageType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.ThemeMode
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.Event
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.EventChannel
import org.koin.android.ext.android.inject
import kotlin.random.Random

class QRActivity : ComponentActivity() {

    private val viewModel by viewModels<PremiumViewModel>()

    private val appStore by inject<AppStore>()

    private var mInterstitialAd: InterstitialAd? = null
    private var mRewardedAd: RewardedAd? = null

    private var isAdShowed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        MobileAds.initialize(this)
        loadInterstitialAd()
        loadRewardedAd()

        setContent {
            val hasAcknowledged = viewModel.hasAcknowledged.collectAsStateWithLifecycle().value

            var language by remember { mutableStateOf(appStore.getSelectedLanguage()) }
            var themeMode by remember { mutableStateOf(appStore.getSelectedThemeMode()) }

            when (
                val event = EventChannel.receiveEvent().collectAsStateWithLifecycle(
                    initialValue = Event.Idle,
                    lifecycle = lifecycle
                ).value
            ) {
                is Event.LanguageChanged -> language = event.language
                is Event.ThemeModeChanged -> themeMode = event.themeMode
                else -> {}
            }

            if (hasAcknowledged != null) {
                QRApp(
                    hasSubscription = hasAcknowledged,
                    isOnBoarding = appStore.isOnBoarding(),
                    language = language,
                    themeMode = themeMode,
                )
            } else {
                BillingLoadingContent(
                    language = language,
                    themeMode = themeMode
                )
            }
        }
    }

    override fun onDestroy() {
        removeInterstitialAd()
        removeRewardedAd()
        super.onDestroy()
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun BillingLoadingContent(
        language: LanguageType,
        themeMode: ThemeMode,
    ) {
        QRTheme(
            hasSubscription = false,
            language = language,
            themeMode = themeMode
        ) {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ) { padding ->
                QRBackground(
                    modifier = Modifier
                        .padding(padding)
                        .consumeWindowInsets(padding),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(MaterialTheme.shapes.large),
                            strokeCap = StrokeCap.Round
                        )
                    }
                }
            }
        }
    }

    fun showAds(hasSubscription: Boolean) {
        if (!hasSubscription && !isAdShowed) {
            if (Random.nextBoolean()) {
                showRewardedAd()
            } else {
                showInterstitialAd()
            }
        }
    }

    private fun loadInterstitialAd() {
        InterstitialAd.load(
            this,
            "ca-app-pub-9612143868526251/9749334951",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(e: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }

    private fun loadRewardedAd() {
        RewardedAd.load(
            this,
            "ca-app-pub-9612143868526251/1703323976",
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(e: LoadAdError) {
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                }
            })
    }

    private fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    mInterstitialAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null

                    loadInterstitialAd()
                }
            }
            mInterstitialAd?.show(this)
        }
    }

    private fun showRewardedAd() {
        if (mRewardedAd != null) {
            mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    mRewardedAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    mRewardedAd = null

                    loadRewardedAd()
                }
            }
            mRewardedAd?.show(this) {}
        }
    }

    private fun removeInterstitialAd() {
        mInterstitialAd?.fullScreenContentCallback = null
        mInterstitialAd = null
    }

    private fun removeRewardedAd() {
        mRewardedAd?.fullScreenContentCallback = null
        mRewardedAd = null
    }
}