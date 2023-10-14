package com.cashix.kotlin.UI.onBoarding.di

import com.cashix.kotlin.UI.onBoarding.data.BoardRepository
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
    fun provideBoardRepository(retrofit: Retrofit): BoardRepository = BoardRepository(retrofit);
}