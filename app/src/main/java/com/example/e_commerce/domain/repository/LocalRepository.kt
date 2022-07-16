package com.example.e_commerce.domain.repository

import com.example.e_commerce.data.entities.user.User
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun insertUserToDatabase(user: User)
    suspend fun getCurrentUser(userId: String): User
    suspend fun login(userName: String, userPassword: String): User
}