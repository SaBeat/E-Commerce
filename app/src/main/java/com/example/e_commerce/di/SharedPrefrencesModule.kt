package com.example.e_commerce.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("getSharedPref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideUserId(sharedPreferences: SharedPreferences): String =
        sharedPreferences.getString("getSharedPref", null).toString()
}