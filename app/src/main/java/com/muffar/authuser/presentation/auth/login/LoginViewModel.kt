package com.muffar.authuser.presentation.auth.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muffar.authuser.domain.model.User
import com.muffar.authuser.domain.repository.LoginResponse
import com.muffar.authuser.domain.use_case.AuthUseCase
import com.muffar.authuser.ui.common.InputWrapper
import com.muffar.authuser.utils.FormValidator
import com.muffar.authuser.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val handle: SavedStateHandle,
) : ViewModel() {

    val email = handle.getStateFlow(EMAIL_KEY, InputWrapper())
    val password = handle.getStateFlow(PASSWORD_KEY, InputWrapper())
    val areInputsValid = combine(email, password) { idNumber, password ->
        idNumber.errorMessage == null && idNumber.value.isNotEmpty()
                && password.errorMessage == null && password.value.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500L), false)

    fun onEmailEntered(input: String) {
        val errorMessage = FormValidator.emailValidator(input)
        handle[EMAIL_KEY] = email.value.copy(value = input, errorMessage = errorMessage)
    }

    fun onPasswordEntered(input: String) {
        val errorMessage = FormValidator.passwordValidator(input)
        handle[PASSWORD_KEY] = password.value.copy(value = input, errorMessage = errorMessage)
    }

    private val _loginResult: MutableStateFlow<LoginResponse> =
        MutableStateFlow(Response.Empty)
    val loginResult: StateFlow<LoginResponse> = _loginResult

    fun login(email: String, password: String) =
        viewModelScope.launch {
            val user = User(email, password)
            authUseCase.loginUser(user).collect {
                _loginResult.value = it
            }
        }

    companion object {
        private const val EMAIL_KEY = "email_key"
        private const val PASSWORD_KEY = "password_key"
    }
}