package com.example.e_commerce.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.e_commerce.data.local.product.CommerceDatabase
import com.example.e_commerce.data.local.product.basket.BasketDao
import com.example.e_commerce.data.local.product.collections.CollectionsDao
import com.example.e_commerce.data.local.product.favorites.FavoriteDao
import com.example.e_commerce.data.local.product.product.ProductDAO
import com.example.e_commerce.data.local.user.UserDao
import com.example.e_commerce.data.repository.LocalRepositoryImpl
import com.example.e_commerce.domain.repository.LocalRepository
import com.example.e_commerce.domain.usecase.local.user.InsertUserToDatabaseUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun providesCommerceDatabase(@ApplicationContext context: Context):CommerceDatabase =
        Room.databaseBuilder(context,CommerceDatabase::class.java,"commerce.db").
        fallbackToDestructiveMigration().
        build()

    @Provides
    @Singleton
    fun providesUserDao(database:CommerceDatabase):UserDao = database.userDao()

    @Provides
    @Singleton
    fun providesBasketDao(database:CommerceDatabase):BasketDao = database.basketDao()

    @Provides
    @Singleton
    fun providesCollectionsDao(database:CommerceDatabase):CollectionsDao = database.collectionsDao()

    @Provides
    @Singleton
    fun providesFavoriteDao(database:CommerceDatabase):FavoriteDao = database.favoriteDao()

    @Provides
    @Singleton
    fun providesProductDao(database:CommerceDatabase):ProductDAO = database.productDao()

}