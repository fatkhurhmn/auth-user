package com.muffar.authuser.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(
    isShowingDialog: Boolean = true,
    dismissOnBackPressed: Boolean = false,
    dismissOnClickOutside: Boolean = false,
) {
    if (isShowingDialog) {
        Dialog(
            onDismissRequest = {},
            DialogProperties(
                dismissOnBackPress = dismissOnBackPressed,
                dismissOnClickOutside = dismissOnClickOutside,
            )
        ) {
            LoadingAnimation()
        }
    }
}