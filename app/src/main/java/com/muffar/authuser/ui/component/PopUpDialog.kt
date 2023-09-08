package com.muffar.authuser.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muffar.authuser.R

@Composable
fun PopUpDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    positiveButtonColor: Color = MaterialTheme.colorScheme.primary,
    negativeButtonColor: Color = MaterialTheme.colorScheme.primary,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 20.sp,
                        letterSpacing = 0.sp
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(
                    text = stringResource(R.string.yes),
                    style = MaterialTheme.typography.labelLarge,
                    color = positiveButtonColor
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(R.string.no),
                    style = MaterialTheme.typography.labelLarge,
                    color = negativeButtonColor
                )
            }
        },
        shape = MaterialTheme.shapes.small,
    )
}