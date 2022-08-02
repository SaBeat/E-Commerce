package com.example.e_commerce.domain.repository

import androidx.room.withTransaction
import com.example.e_commerce.data.local.product.CommerceDatabase
import com.example.e_commerce.data.remote.ProductApi
import com.example.e_commerce.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

//class CacheRepository @Inject constructor(
//    private val api: ProductApi,
//    private val db:CommerceDatabase
//) {
//    val dao = db.productDao()
//
//    fun getCars() = networkBoundResource(
//
//        // Query to return the list of all cars
//        query = {
//            dao.getAllProduct()
//        },
//
//        // Just for testing purpose,
//        // a delay of 2 second is set.
//        fetch = {
//            delay(2000)
//            api.getProducts()
//        },
//
//        saveFetchResult = { product ->
//            db.withTransaction {
//                dao.deleteAllProduct()
//                product.forEach {a ->
//                    dao.insertProduct(a)
//                }
//
//            }
//        }
//    )
//}