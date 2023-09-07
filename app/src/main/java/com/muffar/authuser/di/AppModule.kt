package com.muffar.authuser.di

import com.muffar.authuser.domain.use_case.AuthUseCase
import com.muffar.authuser.domain.use_case.AuthUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideAuthUseCase(authUseCase: AuthUseCaseImpl): AuthUseCase
}