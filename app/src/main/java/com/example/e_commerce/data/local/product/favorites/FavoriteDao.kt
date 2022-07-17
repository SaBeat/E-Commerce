package com.example.e_commerce.data.local.product.favorites

import androidx.room.*
import com.example.e_commerce.data.entities.product.Favorites
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorites: Favorites)

    @Delete
    suspend fun delete(favorites: Favorites)

    @Update
    suspend fun update(favorites: Favorites)

    @Query("Select * from collection_db where currentUserId=:userId")
    fun getFavoriteProducts(userId:String): Flow<List<Favorites>>
}