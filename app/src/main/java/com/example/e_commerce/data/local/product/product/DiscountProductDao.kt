package com.example.e_commerce.data.local.product.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.e_commerce.data.entities.product.DiscountProduct

@Dao
interface DiscountProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscount(product:MutableList<DiscountProduct>)
}