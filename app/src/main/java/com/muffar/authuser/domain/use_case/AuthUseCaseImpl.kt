package com.muffar.authuser.domain.use_case

import com.muffar.authuser.domain.model.User
import com.muffar.authuser.domain.repository.IAuthRepository
import com.muffar.authuser.domain.repository.LoginResponse
import com.muffar.authuser.domain.repository.LogoutResponse
import com.muffar.authuser.domain.repository.RegisterResponse
import com.muffar.authuser.domain.repository.UserResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    private val authRepository: IAuthRepository,
) : AuthUseCase {
    override suspend fun registerUser(user: User): Flow<RegisterResponse> =
        authRepository.registerUser(user)

    override suspend fun loginUser(user: User): Flow<LoginResponse> =
        authRepository.loginUser(user)

    override suspend fun isLoggedIn(): Flow<Boolean> =
        authRepository.isLoggedIn()

    override suspend fun logoutUser(): Flow<LogoutResponse> =
        authRepository.logoutUser()

    override suspend fun getUser(): Flow<UserResponse> =
        authRepository.getUser()
}