package com.muffar.authuser.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.muffar.authuser.R
import com.muffar.authuser.presentation.auth.rememberAuthState
import com.muffar.authuser.presentation.main.component.MainContent
import com.muffar.authuser.ui.component.LoadingAnimation
import com.muffar.authuser.ui.component.LoadingDialog
import com.muffar.authuser.ui.component.PopUpDialog
import com.muffar.authuser.ui.component.TopAppBar
import com.muffar.authuser.utils.Response

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val user = viewModel.user.collectAsState().value
    val logout = viewModel.logoutResult.collectAsState().value
    val state = rememberAuthState()

    Scaffold(
        topBar = {
            TopAppBar(title = stringResource(R.string.app_name))
        },
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (user) {
                is Response.Loading -> {
                    viewModel.getUser()
                    LoadingAnimation(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        circleColor = MaterialTheme.colorScheme.primary
                    )
                }

                is Response.Success -> {
                    val email = user.data?.email ?: ""
                    MainContent(
                        email = email,
                        onLogoutClick = { state.openLogoutDialog = true }
                    )
                }

                is Response.Failure -> {
                    val message = user.e?.localizedMessage ?: ""
                    state.showMessage(message)
                }

                else -> {}
            }

            if (state.openLogoutDialog) {
                PopUpDialog(
                    title = stringResource(R.string.logout),
                    message = stringResource(R.string.logout_message),
                    positiveButtonColor = MaterialTheme.colorScheme.error,
                    onDismiss = { state.openLogoutDialog = false },
                    onConfirm = { viewModel.logoutUser() }
                )
            }

            when (logout) {
                is Response.Loading -> LoadingDialog()
                is Response.Success -> state.intentToAuthActivity()
                else -> {}
            }
        }
    }
}