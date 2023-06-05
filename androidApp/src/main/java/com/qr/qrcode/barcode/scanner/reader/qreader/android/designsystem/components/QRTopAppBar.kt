package com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.windowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation.NavigationTree
import com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation.navigationTree

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRTopAppBar(
    onNavigateUp: () -> Unit = {},
    currentDestination: NavDestination?
) {
    val navigationTree = remember(currentDestination) {
        currentDestination?.route.navigationTree()
    }

    when (navigationTree) {
        NavigationTree.Scanner, NavigationTree.Creator,
        NavigationTree.History, NavigationTree.Settings -> {
            QRTopAppBarContent(
                title = navigationTree.title
            )
        }

        NavigationTree.OnBoarding, NavigationTree.Premium -> {
            // Nothing
        }

        else -> {
            QRTopAppBarContent(
                title = navigationTree.title,
                onNavigateUp = onNavigateUp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRTopAppBarContent(
    title: Int,
    onNavigateUp: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .windowInsetsPadding(windowInsets)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            if (onNavigateUp != null) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = onNavigateUp
                ) {
                    QRIcon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )
    }
}