package com.muffar.authuser.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.muffar.authuser.utils.Response
import com.muffar.authuser.domain.model.User
import kotlinx.coroutines.flow.Flow

typealias RegisterResponse = Response<Boolean>
typealias LoginResponse = Response<Boolean>
typealias LogoutResponse = Response<Boolean>
typealias UserResponse = Response<FirebaseUser?>

interface IAuthRepository {
    suspend fun registerUser(user: User): Flow<RegisterResponse>
    suspend fun loginUser(user: User): Flow<LoginResponse>
    suspend fun isLoggedIn(): Flow<Boolean>
    suspend fun logoutUser(): Flow<LogoutResponse>
    suspend fun getUser(): Flow<UserResponse>
}