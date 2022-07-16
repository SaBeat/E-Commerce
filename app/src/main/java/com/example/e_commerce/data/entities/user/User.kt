package com.example.e_commerce.data.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_db")
data class User (
    @ColumnInfo(name = "username")
    val userName: String?,
    @ColumnInfo(name = "email")
    val userMail: String?,
    @ColumnInfo(name = "password")
    val userPassword: String?,
    @ColumnInfo(name = "currentuser")
    val currentUserId: String? = null,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val userId: Int? = null
)