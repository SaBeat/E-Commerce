package com.example.e_commerce.data.local.product

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_commerce.data.entities.user.User
import com.example.e_commerce.data.local.user.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class CommerceDatabase :RoomDatabase(){
    abstract fun userDao():UserDao
}