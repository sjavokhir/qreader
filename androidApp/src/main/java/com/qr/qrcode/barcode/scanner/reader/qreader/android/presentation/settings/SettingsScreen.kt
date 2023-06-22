package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.gotoUrl
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.shareText
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.GoProContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.AboutScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.FaqScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.FeedbackScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.LanguageScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ManagePermissionsScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.PremiumScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.SoundEffectsScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ThemeModeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.settings.SettingsEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.settings.SettingsState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.settings.SettingsViewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.appUrl
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

@Destination
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    navigator: DestinationsNavigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    QRBackground {
        SettingsScreenContent(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigate = navigator::navigate
        )
    }
}

@Composable
private fun SettingsScreenContent(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    onNavigate: (Direction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.05f)),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { GoProContent { onNavigate(PremiumScreenDestination) } }
        item { GeneralContent(onNavigate) }
        item { ScanControlsContent(state, onEvent, onNavigate) }
        item { GetHelpContent(onNavigate) }
        item { OthersContent(onNavigate) }
    }
}

@Composable
private fun GeneralContent(
    onNavigate: (Direction) -> Unit
) {
    HeaderContent(title = R.string.general) {
        NavigateContent(title = R.string.theme) {
            onNavigate(ThemeModeScreenDestination)
        }

        DividerContent()

        NavigateContent(title = R.string.language) {
            onNavigate(LanguageScreenDestination)
        }
    }
}

@Composable
private fun ScanControlsContent(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    onNavigate: (Direction) -> Unit
) {
    HeaderContent(title = R.string.scan_controls) {
        NavigateContent(
            title = R.string.sound_effects,
            hasSubscription = state.hasSubscription
        ) {
            if (state.hasSubscription) {
                onNavigate(SoundEffectsScreenDestination)
            } else {
                onNavigate(PremiumScreenDestination)
            }
        }

        DividerContent()

        SwitchContent(
            title = R.string.vibrate,
            checked = state.isVibrateChecked,
            onCheckedChange = {
                onEvent(SettingsEvent.CheckVibrate(it))
            }
        )

        DividerContent()

        SwitchContent(
            title = R.string.open_web_pages,
            checked = state.isChromeCustomTabsChecked,
            onCheckedChange = {
                onEvent(SettingsEvent.CheckChromeCustomTabs(it))
            }
        )

        DividerContent()

        SwitchContent(
            title = R.string.chrome_custom_tabs,
            checked = state.isOpenWebPagesChecked,
            onCheckedChange = {
                onEvent(SettingsEvent.CheckOpenWebPages(it))
            }
        )

        DividerContent()

        SwitchContent(
            title = R.string.batch_scan,
            hasSubscription = state.hasSubscription,
            checked = state.isBatchScanChecked,
            onCheckedChange = {
                if (state.hasSubscription) {
                    onEvent(SettingsEvent.CheckBatchScan(it))
                } else {
                    onNavigate(PremiumScreenDestination)
                }
            }
        )
    }
}

@Composable
private fun GetHelpContent(
    onNavigate: (Direction) -> Unit
) {
    HeaderContent(title = R.string.get_help) {
        NavigateContent(title = R.string.feedback) {
            onNavigate(FeedbackScreenDestination)
        }

        DividerContent()

        NavigateContent(title = R.string.frequently_asked_questions) {
            onNavigate(FaqScreenDestination)
        }

        DividerContent()

        NavigateContent(title = R.string.manage_permissions) {
            onNavigate(ManagePermissionsScreenDestination)
        }
    }
}

@Composable
private fun OthersContent(
    onNavigate: (Direction) -> Unit
) {
    val context = LocalContext.current

    HeaderContent(title = R.string.others) {
        NavigateContent(title = R.string.rate_us) {
            context.gotoUrl(appUrl)
        }

        DividerContent()

        NavigateContent(title = R.string.tell_friends) {
            context.shareText("Share")
        }

        DividerContent()

        NavigateContent(title = R.string.about_us) {
            onNavigate(AboutScreenDestination)
        }
    }
}

@Composable
private fun HeaderContent(
    title: Int,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp
        )

        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.background)
        ) {
            content()
        }
    }
}

@Composable
private fun NavigateContent(
    title: Int,
    hasSubscription: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.bodyLarge
        )

        if (!hasSubscription) {
            Image(
                painter = painterResource(id = R.drawable.ic_subscription),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        QRIcon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun SwitchContent(
    title: Int,
    hasSubscription: Boolean = true,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.bodyLarge
        )

        if (!hasSubscription) {
            Image(
                painter = painterResource(id = R.drawable.ic_subscription),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .width(39.dp)
                .height(24.dp)
                .scale(.85f)
        )
    }
}

@Composable
private fun DividerContent() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    )
}