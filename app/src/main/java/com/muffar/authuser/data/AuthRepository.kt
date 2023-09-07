package com.muffar.authuser.data

import com.google.firebase.auth.FirebaseAuth
import com.muffar.authuser.utils.Response
import com.muffar.authuser.domain.model.User
import com.muffar.authuser.domain.repository.IAuthRepository
import com.muffar.authuser.domain.repository.LoginResponse
import com.muffar.authuser.domain.repository.LogoutResponse
import com.muffar.authuser.domain.repository.RegisterResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
) : IAuthRepository {
    override suspend fun registerUser(user: User): Flow<RegisterResponse> = callbackFlow {
        trySend(Response.Loading)
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(Response.Success(true))
                    auth.signOut()
                } else {
                    trySend(Response.Failure(task.exception))
                }
                close()
            }
        awaitClose()
    }

    override suspend fun loginUser(user: User): Flow<LoginResponse> = callbackFlow {
        trySend(Response.Loading)
        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(Response.Success(true))
                } else {
                    trySend(Response.Failure(task.exception))
                }
                close()
            }
        awaitClose()
    }

    override suspend fun isLoggedIn(): Flow<Boolean> = callbackFlow {
        val user = auth.currentUser
        trySend(user != null)
        awaitClose()
    }

    override suspend fun logoutUser(): Flow<LogoutResponse> = callbackFlow {
        trySend(Response.Loading)
        auth.signOut()
        trySend(Response.Success(true))
        awaitClose()
    }
}