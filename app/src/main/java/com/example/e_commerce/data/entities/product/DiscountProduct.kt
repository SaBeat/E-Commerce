package com.example.e_commerce.data.entities.product

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "discount_product_db")
data class DiscountProduct(
    @ColumnInfo(name = "productName")
    var productTitle: String?,
    @ColumnInfo(name = "productDescription")
    var productDescription: String?,
    @ColumnInfo(name = "productCategory")
    var productCategory: String?,
    @ColumnInfo(name = "productPrice")
    val productPrice: String?,
    @ColumnInfo(name = "productImage")
    val productImage: String?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "productId")
    val productId: Int? = null
) : Parcelable

fun DiscountProduct.toProduct() :Product{
    return Product(
        productTitle = productTitle,
        productDescription = productDescription,
        productCategory = productCategory,
        productPrice = productPrice,
        productImage = productImage,
        productId = productId
    )
}