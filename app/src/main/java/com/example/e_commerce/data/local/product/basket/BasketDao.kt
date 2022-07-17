package com.example.e_commerce.data.local.product.basket

import androidx.room.*
import com.example.e_commerce.data.entities.product.Basket
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(basket: Basket)

    @Delete
    suspend fun delete(basket: Basket)

    @Update
    suspend fun update(basket: Basket)

    @Query("Select * from basket_db where currentUserId = :currentUserId")
    suspend fun getBasketProducts(currentUserId:String): Flow<List<Basket>>
}