package com.muffar.authuser.presentation.auth.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muffar.authuser.R
import com.muffar.authuser.presentation.auth.register.component.RegisterForm
import com.muffar.authuser.presentation.auth.rememberAuthState
import com.muffar.authuser.ui.component.LoadingDialog
import com.muffar.authuser.ui.component.TextButton
import com.muffar.authuser.ui.component.TextMessage
import com.muffar.authuser.utils.Response

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onLoginNavigate: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val areInputsValid by viewModel.areInputsValid.collectAsState()
    val result = viewModel.registerResult.collectAsState(Response.Empty).value
    val state = rememberAuthState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            result.let { result ->
                if (result is Response.Failure) {
                    result.e?.localizedMessage?.let {
                        TextMessage(
                            message = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                } else {
                    TextMessage(
                        message = stringResource(R.string.register_please),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            RegisterForm(
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                areInputsValid = areInputsValid,
                viewModel = viewModel,
                state = state,
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                text = stringResource(R.string.login),
                onClick = onLoginNavigate,
            )
        }

        LaunchedEffect(result) {
            when (result) {
                is Response.Success -> {
                    state.showMessage(state.context.getString(R.string.register_message))
                    onLoginNavigate()
                }

                is Response.Failure ->
                    result.e?.localizedMessage?.let { state.showMessage(it) }

                else -> {}
            }
        }

        if (result is Response.Loading) {
            LoadingDialog()
        }
    }
}