package com.muffar.authuser.presentation.auth.register


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muffar.authuser.domain.model.User
import com.muffar.authuser.domain.repository.RegisterResponse
import com.muffar.authuser.domain.use_case.AuthUseCase
import com.muffar.authuser.ui.common.InputWrapper
import com.muffar.authuser.utils.FormValidator
import com.muffar.authuser.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val handle: SavedStateHandle,
) : ViewModel() {
    val email = handle.getStateFlow(EMAIL_KEY, InputWrapper())
    val password = handle.getStateFlow(PASSWORD_KEY, InputWrapper())
    val confirmPassword = handle.getStateFlow(CONFIRM_PASSWORD_KEY, InputWrapper())

    private val _registerResult: MutableStateFlow<RegisterResponse> =
        MutableStateFlow(Response.Empty)
    val registerResult: StateFlow<RegisterResponse> = _registerResult

    val areInputsValid = combine(
        email,
        password,
        confirmPassword,
    ) { field ->
        field.all { it.errorMessage == null && it.value.isNotEmpty() }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500L), false)

    fun onEmailEntered(input: String) {
        val errorMessage = FormValidator.emailValidator(input)
        handle[EMAIL_KEY] = email.value.copy(value = input, errorMessage = errorMessage)
    }

    fun onPasswordEntered(input: String) {
        val errorMessage = FormValidator.passwordValidator(input)
        val errorMessageConfirm =
            if (confirmPassword.value.value != "") {
                FormValidator.confirmPasswordValidator(input, confirmPassword.value.value)
            } else {
                null
            }
        handle[PASSWORD_KEY] = password.value.copy(value = input, errorMessage = errorMessage)
        handle[CONFIRM_PASSWORD_KEY] =
            confirmPassword.value.copy(errorMessage = errorMessageConfirm)
    }

    fun onConfirmPasswordEntered(input: String) {
        val errorMessage = FormValidator.confirmPasswordValidator(input, password.value.value)
        handle[CONFIRM_PASSWORD_KEY] =
            confirmPassword.value.copy(value = input, errorMessage = errorMessage)
    }

    fun registerUser(email: String, password: String) {
        val user = User(email, password)
        viewModelScope.launch {
            authUseCase.registerUser(user).collect {
                _registerResult.value = it
            }
        }
    }

    companion object {
        private const val EMAIL_KEY = "email_key"
        private const val PASSWORD_KEY = "password_key"
        private const val CONFIRM_PASSWORD_KEY = "confirm_password_key"
    }
}