package com.webtechsolution.ghumantey.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {
    @Binds
    abstract fun getRoomDb(@ApplicationContext context: Context): Context
}