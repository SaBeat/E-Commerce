package com.example.e_commerce.presentation.basket

import com.example.e_commerce.data.entities.product.Basket

interface BasketDeleteClickListener {
    fun deleteBasket(basket: Basket)
}