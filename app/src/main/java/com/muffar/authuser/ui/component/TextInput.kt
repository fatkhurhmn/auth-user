package com.muffar.authuser.ui.component

import android.view.KeyEvent.ACTION_DOWN
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.muffar.authuser.R
import com.muffar.authuser.ui.common.InputWrapper

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    label: String,
    inputWrapper: InputWrapper,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine : Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val passwordInput = keyboardType == KeyboardType.Password
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onPreviewKeyEvent {
                    if (it.key == Key.Tab && it.nativeKeyEvent.action == ACTION_DOWN) {
                        focusManager.moveFocus(FocusDirection.Down)
                        true
                    } else {
                        false
                    }
                },
            value = inputWrapper.value,
            isError = inputWrapper.errorMessage != null,
            onValueChange = onValueChange,
            shape = MaterialTheme.shapes.small,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 22.sp,
                letterSpacing = 0.3.sp,
            ),
            singleLine = singleLine,
            visualTransformation = if (passwordVisible || !passwordInput) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (passwordInput) {
                    val visibleIcon = if (passwordVisible) {
                        Icons.Default.Visibility
                    } else {
                        Icons.Default.VisibilityOff
                    }
                    val description =
                        if (passwordVisible) stringResource(R.string.hide_password)
                        else stringResource(R.string.show_password)

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = visibleIcon, description)
                    }
                } else {
                    trailingIcon?.invoke()
                }
            },
        )
        if (inputWrapper.errorMessage != null) {
            Text(
                text = stringResource(inputWrapper.errorMessage),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}