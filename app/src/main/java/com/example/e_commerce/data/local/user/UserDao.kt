package com.example.e_commerce.data.local.user

import androidx.room.*
import com.example.e_commerce.data.entities.user.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("Select * from user_db where currentuser = :userId")
    suspend fun getCurrentUser(userId: String): User

    @Query("Select * from user_db where username = :user_email and password = :user_password")
    suspend fun login(user_email: String, user_password: String): User
}