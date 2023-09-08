package com.muffar.authuser.presentation.auth.login.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.muffar.authuser.R
import com.muffar.authuser.presentation.auth.AuthState
import com.muffar.authuser.presentation.auth.login.LoginViewModel
import com.muffar.authuser.ui.common.InputWrapper
import com.muffar.authuser.ui.component.TextInput

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    email: InputWrapper,
    password: InputWrapper,
    areInputsValid: Boolean,
    viewModel: LoginViewModel,
    state: AuthState,
) {
    Column(
        modifier = modifier
    ) {
        TextInput(
            label = stringResource(R.string.email),
            inputWrapper = email,
            onValueChange = viewModel::onEmailEntered,
            keyboardType = KeyboardType.Email,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextInput(
            label = stringResource(R.string.password),
            inputWrapper = password,
            onValueChange = viewModel::onPasswordEntered,
            keyboardType = KeyboardType.Password,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(14.dp))
        Button(
            onClick = {
                viewModel.login(email.value.trim(), password.value.trim())
                state.hideKeyboard()
            },
            shape = MaterialTheme.shapes.medium,
            enabled = areInputsValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(stringResource(R.string.login))
        }
    }
}