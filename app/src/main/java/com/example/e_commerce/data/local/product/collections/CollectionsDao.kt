package com.example.e_commerce.data.local.product.collections

import androidx.room.*
import com.example.e_commerce.data.entities.product.Collection
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: Collection)

    @Delete
    suspend fun delete(collection: Collection)

    @Update
    suspend fun update(collection: Collection)

    @Query("Select * from collection_db where currentUserId=:currentUserId")
    suspend fun getCollectionsProduct(currentUserId:String): Flow<List<Collection>>
}