package com.muffar.authuser.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muffar.authuser.domain.use_case.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
) : ViewModel() {

    private val _currentUser: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val currentUser: StateFlow<Boolean> get() = _currentUser

    init {
        isLoggedIn()
    }

    private fun isLoggedIn() {
        viewModelScope.launch {
            authUseCase.isLoggedIn().collect {
                _currentUser.value = it
            }
        }
    }
}