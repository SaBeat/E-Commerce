package com.example.e_commerce.di

import com.example.e_commerce.data.local.product.product.ProductDAO
import com.example.e_commerce.data.remote.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun providesRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://canerture.com/api/ecommerce/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun providesProductApi(retrofit: Retrofit):ProductApi{
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun providesGsonConvertory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }
}