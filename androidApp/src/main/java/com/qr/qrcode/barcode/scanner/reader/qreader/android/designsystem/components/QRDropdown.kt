package com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.TopicModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRDropdown(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    hint: String,
    selectedOption: TopicModel,
    onSelectedOption: (TopicModel) -> Unit,
    options: List<TopicModel>
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        QRTextField(
            modifier = Modifier.menuAnchor(),
            value = selectedOption.title,
            onValueChange = {},
            placeholder = hint,
            hint = hint,
            readOnly = true,
            trailingIcon = {
                QRIcon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            options.forEach { option ->
                Column {
                    DropdownMenuItem(
                        text = {
                            Column {
                                Text(
                                    text = option.title,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        },
                        onClick = { onSelectedOption(option) },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .padding(ExposedDropdownMenuDefaults.ItemContentPadding),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}