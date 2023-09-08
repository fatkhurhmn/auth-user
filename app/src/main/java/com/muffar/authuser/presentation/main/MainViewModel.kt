package com.muffar.authuser.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muffar.authuser.domain.repository.LogoutResponse
import com.muffar.authuser.domain.repository.UserResponse
import com.muffar.authuser.domain.use_case.AuthUseCase
import com.muffar.authuser.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
) : ViewModel() {

    private val _isLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    private val _logoutResult: MutableStateFlow<LogoutResponse> = MutableStateFlow(Response.Empty)
    val logoutResult: StateFlow<LogoutResponse> = _logoutResult

    private val _user: MutableStateFlow<UserResponse> = MutableStateFlow(Response.Loading)
    val user: StateFlow<UserResponse> get() = _user

    init {
        isLoggedIn()
    }

    fun logoutUser() {
        viewModelScope.launch {
            authUseCase.logoutUser().collect {
                _logoutResult.value = it
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            authUseCase.getUser().collect {
                _user.value = it
            }
        }
    }

    private fun isLoggedIn() {
        viewModelScope.launch {
            authUseCase.isLoggedIn().collect {
                _isLoggedIn.value = it
            }
        }
    }
}