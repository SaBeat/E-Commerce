package com.example.e_commerce.data.repository

import com.example.e_commerce.data.entities.user.User
import com.example.e_commerce.data.local.user.UserDao
import com.example.e_commerce.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) :LocalRepository{
    override suspend fun insertUserToDatabase(user: User) {
         userDao.inserUser(user)
    }

    override suspend fun getCurrentUser(userId: String): User {
        return userDao.getCurrentUser(userId)
    }
}