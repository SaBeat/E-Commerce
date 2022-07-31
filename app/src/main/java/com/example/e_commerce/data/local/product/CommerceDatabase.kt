package com.example.e_commerce.data.local.product

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_commerce.data.entities.product.*
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.user.User
import com.example.e_commerce.data.local.product.basket.BasketDao
import com.example.e_commerce.data.local.product.collections.CollectionsDao
import com.example.e_commerce.data.local.product.favorites.FavoriteDao
import com.example.e_commerce.data.local.product.product.ProductDAO
import com.example.e_commerce.data.local.product.purchased.PurchasedDao
import com.example.e_commerce.data.local.user.UserDao

@Database(entities = [User::class, Basket::class,Collection::class,Favorites::class, Product::class,Purchased::class], version = 4, exportSchema = false)
abstract class CommerceDatabase :RoomDatabase(){
    abstract fun userDao():UserDao
    abstract fun basketDao():BasketDao
    abstract fun collectionsDao():CollectionsDao
    abstract fun favoriteDao():FavoriteDao
    abstract fun productDao():ProductDAO
    abstract fun purchasedDao():PurchasedDao
}