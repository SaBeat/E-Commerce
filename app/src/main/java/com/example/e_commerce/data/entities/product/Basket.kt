package com.example.e_commerce.data.entities.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_db")
data class Basket(
    @ColumnInfo(name = "productTitle")
    var productName: String?,
    @ColumnInfo(name = "productDescription")
    var productDescription: String?,
    @ColumnInfo(name = "productCount")
    val productCount: String?,
    @ColumnInfo(name = "currentUserId")
    val currentUserId: String?,
    @ColumnInfo(name = "productPrice")
    val productPrice: String?,
    @ColumnInfo(name = "productImage")
    val productImage: String?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "productId")
    val productId: Int? = null
)