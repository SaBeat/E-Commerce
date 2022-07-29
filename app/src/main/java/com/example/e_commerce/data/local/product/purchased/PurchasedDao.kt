package com.example.e_commerce.data.local.product.purchased

import androidx.room.*
import com.example.e_commerce.data.entities.product.Purchased
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchasedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(purchased: Purchased)

    @Delete
    suspend fun delete(purchased: Purchased)

    @Update
    suspend fun update(purchased: Purchased)

    @Query("Select * from purchaed_db where currentUserId=:userId")
    fun getPurchasedProducts(userId:String): Flow<List<Purchased>>
}