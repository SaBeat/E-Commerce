package com.example.e_commerce.data.entities.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collection_db")
data class Favorites(
    @ColumnInfo(name = "productName")
    var productName: String?,
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
