package com.muffar.authuser.presentation.auth

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.muffar.authuser.presentation.main.MainActivity

class AuthState(
    openLogoutDialog: Boolean,
    val context: Context,
    private val keyboardController: SoftwareKeyboardController?,
) {
    var openLogoutDialog by mutableStateOf(openLogoutDialog)

    fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun intentToMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun intentToAuthActivity() {
        val intent = Intent(context, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun hideKeyboard() {
        keyboardController?.hide()
    }
}

@Composable
fun rememberAuthState(
    openLogoutDialog: Boolean = false,
    context: Context = LocalContext.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
): AuthState =
    remember(context, keyboardController) {
        AuthState(
            openLogoutDialog = openLogoutDialog,
            context = context,
            keyboardController = keyboardController,
        )
    }