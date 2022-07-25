package com.example.e_commerce.presentation.home

import com.example.e_commerce.data.entities.product.Product

interface OnProductClickListener {

    fun productClick(product:Product)
}