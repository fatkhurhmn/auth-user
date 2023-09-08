package com.muffar.authuser.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muffar.authuser.presentation.auth.login.LoginScreen

@Composable
fun AuthNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                onRegisterNavigate = { navController.navigate(AuthScreen.Register.route) }
            )
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("auth/login")
    object Register : AuthScreen("auth/register")
}