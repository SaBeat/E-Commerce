package com.example.e_commerce.domain.repository

import com.example.e_commerce.data.entities.product.*
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.user.User
import com.example.e_commerce.networkBoundResource
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun insertUserToDatabase(user: User)
    suspend fun getCurrentUser(userId: String): User
    suspend fun login(userName: String, userPassword: String): User
    suspend fun insertProductToDatabase(product: List<Product>)
    suspend fun insertProductToBasket(basket: Basket)
    suspend fun insertProductToCollection(collection: Collection)
    suspend fun insertProductToFavorites(favorites: Favorites)
    suspend fun insertProductToPurchased(purchased: Purchased)
    suspend fun insertDiscountProductToDatabase(productsItem: MutableList<DiscountProduct>)
    suspend fun deleteProductFromBasket(basket: Basket)
    suspend fun deleteProductFromCollection(collection: Collection)
    suspend fun deleteProductFromFavorite(favorites: Favorites)
    suspend fun getAllProduct():Flow<List<Product>>
    suspend fun getProductsByDescription(description:String):Flow<List<Product>>
    suspend fun getCollectionProduct(userId:String):Flow<List<Collection>>
    suspend fun getFavoritesProduct(userId:String):Flow<List<Favorites>>
    suspend fun getBasketProduct(userId:String):Flow<List<Basket>>
    suspend fun getPurchasedProduct(userId:String):Flow<List<Purchased>>

}