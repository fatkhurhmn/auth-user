package com.muffar.authuser.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import com.muffar.authuser.ui.component.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.muffar.authuser.R
import com.muffar.authuser.presentation.auth.rememberAuthState
import com.muffar.authuser.ui.component.LoadingAnimation
import com.muffar.authuser.utils.Response

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val user = viewModel.user.collectAsState().value
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
                    Column(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            text = stringResource(R.string.welcome, email),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.DarkGray
                        )
                    }
                }

                is Response.Failure -> {
                    val message = user.e?.localizedMessage ?: ""
                    state.showMessage(message)
                }

                else -> {}
            }
        }
    }
}