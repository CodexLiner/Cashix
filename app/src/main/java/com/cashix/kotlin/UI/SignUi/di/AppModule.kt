package com.cashix.kotlin.UI.SignUi.di

import com.cashix.kotlin.UI.SignUi.data.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAuthRepository(retrofit: Retrofit) : AuthRepository = AuthRepository(retrofit)
}