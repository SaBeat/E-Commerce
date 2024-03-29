package com.example.e_commerce.data.repository

import com.example.e_commerce.data.entities.product.*
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.user.User
import com.example.e_commerce.data.local.product.basket.BasketDao
import com.example.e_commerce.data.local.product.collections.CollectionsDao
import com.example.e_commerce.data.local.product.favorites.FavoriteDao
import com.example.e_commerce.data.local.product.product.DiscountProductDao
import com.example.e_commerce.data.local.product.product.ProductDAO
import com.example.e_commerce.data.local.product.purchased.PurchasedDao
import com.example.e_commerce.data.local.user.UserDao
import com.example.e_commerce.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val basketDao: BasketDao,
    private val favoriteDao: FavoriteDao,
    private val collectionsDao: CollectionsDao,
    private val productDAO: ProductDAO,
    private val discountProductDAO: DiscountProductDao,
    private val purchasedDao: PurchasedDao
) :LocalRepository{

    override suspend fun insertUserToDatabase(user: User) {
         userDao.insertUser(user)
    }

    override suspend fun getCurrentUser(userId: String): User {
        return userDao.getCurrentUser(userId)
    }

    override suspend fun login(userName: String, userPassword: String): User =
        userDao.login(userName, userPassword)

    override suspend fun insertProductToDatabase(product: List<Product>) {
        productDAO.insertProduct(product)
    }

    override suspend fun insertDiscountProductToDatabase(productsItem: MutableList<DiscountProduct>) {
        discountProductDAO.insertDiscount(productsItem)
    }

    override suspend fun insertProductToBasket(basket: Basket) {
        basketDao.insert(basket)
    }

    override suspend fun insertProductToCollection(collection: Collection) {
        collectionsDao.insert(collection)
    }

    override suspend fun insertProductToFavorites(favorites: Favorites) {
        favoriteDao.insert(favorites)
    }

    override suspend fun insertProductToPurchased(purchased: Purchased) {
        purchasedDao.insert(purchased)
    }

    override suspend fun deleteProductFromBasket(basket: Basket) {
        basketDao.delete(basket)
    }

    override suspend fun deleteProductFromCollection(collection: Collection) {
        collectionsDao.delete(collection)
    }

    override suspend fun deleteProductFromFavorite(favorites: Favorites) {
        favoriteDao.delete(favorites)
    }

    override suspend fun getAllProduct(): Flow<List<Product>> {
        return productDAO.getAllProduct()
    }

    override suspend fun getProductsByDescription(description: String): Flow<List<Product>> {
        return productDAO.getProductByDescription(description)
    }

    override suspend fun getCollectionProduct(userId: String): Flow<List<Collection>> {
        return collectionsDao.getCollectionsProduct(userId)
    }

    override suspend fun getFavoritesProduct(userId: String): Flow<List<Favorites>> {
        return favoriteDao.getFavoriteProducts(userId)
    }

    override suspend fun getBasketProduct(userId: String): Flow<List<Basket>> {
        return basketDao.getBasketProducts(userId)
    }

    override suspend fun getPurchasedProduct(userId: String): Flow<List<Purchased>> {
        return purchasedDao.getPurchasedProducts(userId)
    }
}