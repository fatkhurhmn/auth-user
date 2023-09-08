package com.muffar.authuser.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun TextMessage(
    modifier: Modifier = Modifier,
    message: String,
    color: Color = MaterialTheme.colorScheme.outline,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        text = message,
        color = color,
        style = MaterialTheme.typography.bodyMedium.copy(
            lineHeight = 18.sp,
            fontSize = 14.sp
        ),
        modifier = modifier.fillMaxWidth(),
        textAlign = textAlign
    )
}