package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.about.AboutScreen
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.faq.FaqScreen
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.feedback.FeedbackScreen
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.language.LanguageScreen
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.managePermissions.ManagePermissionsScreen
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.manageSubscription.ManageSubscriptionScreen
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.onBoarding.OnBoardingScreen
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.premium.PremiumScreen
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.settings.SettingsScreen
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.sound.SoundEffectsScreen
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.faq.FaqViewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.feedback.FeedbackViewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.language.LanguageViewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.onBoarding.OnBoardingViewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.premium.PremiumViewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.settings.SettingsViewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.sound.SoundEffectsViewModel

fun NavGraphBuilder.onBoardingGraph(
    controller: NavHostController
) {
    composable(route = NavigationTree.OnBoarding.route) {
        val viewModel: OnBoardingViewModel = viewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        QRBackground {
            OnBoardingScreen(
                state = state,
                onEvent = viewModel::onEvent,
                onNavigate = controller::navigateTo
            )
        }
    }
}

fun NavGraphBuilder.scannerGraph(
    controller: NavHostController
) {
    composable(route = NavigationTree.Scanner.route) {
        QRBackground {
            Text(text = "Scanner")
        }
    }
}

fun NavGraphBuilder.creatorGraph(
    controller: NavHostController
) {
    composable(route = NavigationTree.Creator.route) {
        QRBackground {
            Text(text = "Creator")
        }
    }
}

fun NavGraphBuilder.historyGraph(
    controller: NavHostController
) {
    composable(route = NavigationTree.History.route) {
        QRBackground {
            Text(text = "History")
        }
    }
}

fun NavGraphBuilder.settingsGraph(
    controller: NavHostController
) {
    composable(route = NavigationTree.Settings.route) {
        val viewModel: SettingsViewModel = viewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        QRBackground {
            SettingsScreen(
                state = state,
                onEvent = viewModel::onEvent,
                onNavigate = controller::navigateTo
            )
        }
    }

    composable(route = NavigationTree.SoundEffects.route) {
        val viewModel: SoundEffectsViewModel = viewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        QRBackground {
            SoundEffectsScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }

    composable(route = NavigationTree.Language.route) {
        val viewModel: LanguageViewModel = viewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        QRBackground {
            LanguageScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }

    composable(route = NavigationTree.Feedback.route) {
        val viewModel: FeedbackViewModel = viewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        QRBackground {
            FeedbackScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }

    composable(route = NavigationTree.FrequentlyAskedQuestions.route) {
        val viewModel: FaqViewModel = viewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        QRBackground {
            FaqScreen(state)
        }
    }

    composable(route = NavigationTree.ManagePermissions.route) {
        QRBackground {
            ManagePermissionsScreen()
        }
    }

    composable(route = NavigationTree.ManageSubscription.route) {
        QRBackground {
            ManageSubscriptionScreen()
        }
    }

    composable(route = NavigationTree.AboutUs.route) {
        QRBackground {
            AboutScreen()
        }
    }
}

fun NavGraphBuilder.premiumGraph(
    controller: NavHostController
) {
    composable(route = NavigationTree.Premium.route) {
        val viewModel: PremiumViewModel = viewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        QRBackground {
            PremiumScreen(
                state = state,
                onEvent = viewModel::onEvent,
                onNavigateUp = { controller.navigateUp() }
            )
        }
    }
}