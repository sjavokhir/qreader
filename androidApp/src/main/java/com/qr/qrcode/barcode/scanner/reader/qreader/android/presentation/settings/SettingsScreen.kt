package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.settings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.GoProContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation.NavigationTree
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.settings.SettingsEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.settings.SettingsState

@Composable
fun SettingsScreen(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    onNavigate: (NavigationTree) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.05f)),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { GoProContent { onNavigate(NavigationTree.Premium) } }
        item { CustomSettingContent(state, onEvent, onNavigate) }
        item { ScanSettingContent(state, onEvent, onNavigate) }
        item { ResultSettingContent(onNavigate) }
        item { GetHelpContent(onNavigate) }
        item { OthersContent(onNavigate) }
    }
}

@Composable
private fun CustomSettingContent(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    onNavigate: (NavigationTree) -> Unit
) {
    HeaderContent(title = R.string.custom_setting) {
        SwitchContent(
            title = R.string.app_lock,
            hasSubscription = state.hasSubscription,
            checked = state.isAppLockChecked,
            onCheckedChange = {
                if (state.hasSubscription) {
                    onEvent(SettingsEvent.CheckAppLock(it))
                } else {
                    onNavigate(NavigationTree.Premium)
                }
            }
        )

        DividerContent()

        ColorContent(
            title = R.string.background_color,
            hasSubscription = state.hasSubscription,
            hexColor = "#FFFBFF"
        )

        DividerContent()

        ColorContent(
            title = R.string.foreground_color,
            hasSubscription = state.hasSubscription,
            hexColor = "#201A19"
        )
    }
}

@Composable
private fun ScanSettingContent(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    onNavigate: (NavigationTree) -> Unit
) {
    HeaderContent(title = R.string.scan_setting) {
        NavigateContent(
            title = R.string.sound_effects,
            hasSubscription = state.hasSubscription
        ) {
            if (state.hasSubscription) {
                onNavigate(NavigationTree.SoundEffects)
            } else {
                onNavigate(NavigationTree.Premium)
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
                    onNavigate(NavigationTree.Premium)
                }
            }
        )
    }
}

@Composable
private fun ResultSettingContent(
    onNavigate: (NavigationTree) -> Unit
) {
    HeaderContent(title = R.string.result_setting) {
        NavigateContent(title = R.string.language) {
            onNavigate(NavigationTree.Language)
        }
    }
}

@Composable
private fun GetHelpContent(
    onNavigate: (NavigationTree) -> Unit
) {
    HeaderContent(title = R.string.get_help) {
        NavigateContent(title = R.string.feedback) {
            onNavigate(NavigationTree.Feedback)
        }

        DividerContent()

        NavigateContent(title = R.string.frequently_asked_questions) {
            onNavigate(NavigationTree.FrequentlyAskedQuestions)
        }

        DividerContent()

        NavigateContent(title = R.string.manage_permissions) {
            onNavigate(NavigationTree.ManagePermissions)
        }

        DividerContent()

        NavigateContent(title = R.string.manage_subscription) {
            onNavigate(NavigationTree.ManageSubscription)
        }
    }
}

@Composable
private fun OthersContent(
    onNavigate: (NavigationTree) -> Unit
) {
    HeaderContent(title = R.string.others) {
        NavigateContent(title = R.string.rate_us) {
            onNavigate(NavigationTree.RateUs)
        }

        DividerContent()

        NavigateContent(title = R.string.tell_friends) {
            onNavigate(NavigationTree.TellFriends)
        }

        DividerContent()

        NavigateContent(title = R.string.about_us) {
            onNavigate(NavigationTree.AboutUs)
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
            .clickable(onClick = onClick)
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
private fun ColorContent(
    title: Int,
    hasSubscription: Boolean = true,
    hexColor: String
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

        Canvas(
            modifier = Modifier
                .size(size = 24.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
        ) {
            drawCircle(color = Color(hexColor.toColorInt()))
        }
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