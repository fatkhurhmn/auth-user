package com.muffar.authuser.domain.use_case

import com.muffar.authuser.domain.repository.LoginResponse
import com.muffar.authuser.domain.repository.LogoutResponse
import com.muffar.authuser.domain.repository.RegisterResponse
import com.muffar.authuser.domain.model.User
import com.muffar.authuser.domain.repository.UserResponse
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    suspend fun registerUser(user: User): Flow<RegisterResponse>
    suspend fun loginUser(user: User): Flow<LoginResponse>
    suspend fun isLoggedIn(): Flow<Boolean>
    suspend fun logoutUser(): Flow<LogoutResponse>
    suspend fun getUser(): Flow<UserResponse>
}