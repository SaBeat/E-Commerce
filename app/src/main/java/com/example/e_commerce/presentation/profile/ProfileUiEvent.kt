package com.example.e_commerce.presentation.profile

sealed class ProfileUiEvent{
    data class GetCurrentUserFromDatabase(val userId:String):ProfileUiEvent()
    data class GetFavoriteItemCount(val userId: String):ProfileUiEvent()
    data class GetCollectionItemCount(val userId: String):ProfileUiEvent()
    data class GetPurchasedItemCount(val userId: String):ProfileUiEvent()
}