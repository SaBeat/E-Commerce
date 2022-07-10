package com.example.e_commerce.di

import com.example.e_commerce.data.local.user.UserDao
import com.example.e_commerce.data.repository.FirebaseAuthRepositoryImpl
import com.example.e_commerce.data.repository.LocalRepositoryImpl
import com.example.e_commerce.domain.repository.FirebaseAuthRepository
import com.example.e_commerce.domain.repository.LocalRepository
import com.example.e_commerce.domain.usecase.local.user.GetCurrentUserFromDatabaseUseCase
import com.example.e_commerce.domain.usecase.local.user.InsertUserToDatabaseUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocalRepository(localRepository: LocalRepositoryImpl): LocalRepository

    @Binds
    @Singleton
    abstract fun bindFirebaseAuthRepository(firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl): FirebaseAuthRepository

}