package com.example.e_commerce.data.local.product.product

import androidx.room.*
import com.example.e_commerce.data.entities.product.Product
import com.example.e_commerce.data.entities.product.ProductsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: List<Product>)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM product_db")
    fun getAllProduct(): Flow<List<Product>>

    @Query("SELECT * FROM product_db WHERE productCategory = :category")
    fun getProductByCategories(category: String): Flow<List<Product>>

    @Query("Delete from product_db")
    suspend fun deleteAllProduct()

    @Query("SELECT * FROM product_db WHERE productDescription LIKE :description")
    fun getProductByDescription(description: String): Flow<List<Product>>
}