package com.example.e_commerce.presentation.profile

import com.example.e_commerce.data.entities.user.User

data class ProfileUiState(
    val user:User? = null,
    val error:String? = null,
    val favoriteCount:Int?=null,
    val collectionCount:Int?=null,
    val purchasedCount:Int?=null
)
