package com.example.e_commerce.data.remote

import com.example.e_commerce.data.entities.product.ProductsItem
import com.example.e_commerce.data.model.CRUDResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductApi {

    @POST("add_to_bag.php")
    @FormUrlEncoded
    suspend fun addProductToBag(
        @Field("user") user: String,
        @Field("title") title: String,
        @Field("price") price: Double,
        @Field("description") description: String,
        @Field("category") category: String,
        @Field("image") image: String,
        @Field("rate") rate: Double,
        @Field("count") count: Int,
        @Field("sale_state") sale_state: Int,
    ): CRUDResponse

    @POST("get_bag_products_by_user.php")
    @FormUrlEncoded
    suspend fun getBagProductsByUser(
        @Field("user") user: String,
    ): List<ProductsItem>

    @POST("delete_to_bag.php")
    @FormUrlEncoded
    suspend fun deleteFromBag(
        @Field("id") id: Int
    ): CRUDResponse

    @POST("get_categories_by_user.php")
    suspend fun getCategoriesByUser(@Field("user") user: String): List<String>

    @GET("get_products.php")
    suspend fun getProducts(): List<ProductsItem>

    @POST("get_products_by_user.php")
    @FormUrlEncoded
    suspend fun getProductsByName(
        @Field("user") user: String
    ): List<ProductsItem>

    @POST("get_products_by_user_and_category.php")
    @FormUrlEncoded
    suspend fun getProductsByCategories(
        @Field("user") user: String,
        @Field("category") category: String
    ): List<ProductsItem>

    @GET("get_sale_products.php")
    @FormUrlEncoded
    suspend fun getSaleProducts(): List<ProductsItem>

}