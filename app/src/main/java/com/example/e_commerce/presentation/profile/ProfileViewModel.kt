package com.example.e_commerce.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.domain.usecase.local.product.GetCollectionsItemsCountUseCase
import com.example.capstoneproject.domain.usecase.local.product.GetFavoritesItemsCountUseCase
import com.example.e_commerce.common.Resource
import com.example.e_commerce.domain.usecase.local.product.GetPurchasedItemsCountUseCase
import com.example.e_commerce.domain.usecase.local.user.GetCurrentUserFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserFromDatabase:GetCurrentUserFromDatabaseUseCase,
    private val getFavoritesItemsCountUseCase: GetFavoritesItemsCountUseCase,
    private val getCollectionsItemsCountUseCase: GetCollectionsItemsCountUseCase,
    private val getPurchasedItemsCountUseCase: GetPurchasedItemsCountUseCase
) :ViewModel(){

    private val uiState = MutableStateFlow(ProfileUiState())
    val _uiState: StateFlow<ProfileUiState> = uiState.asStateFlow()


    fun handleEvent(profileUiEvent: ProfileUiEvent){
        when(profileUiEvent){
            is ProfileUiEvent.GetCurrentUserFromDatabase -> {
                getCurrentUserFromDatabase(profileUiEvent.userId)
            }
            is ProfileUiEvent.GetFavoriteItemCount -> {
                favoriteItemCount(profileUiEvent.userId)
            }
            is ProfileUiEvent.GetCollectionItemCount -> {
                collectionItemCount(profileUiEvent.userId)
            }
            is ProfileUiEvent.GetPurchasedItemCount -> {
                purchasedItemCount(profileUiEvent.userId)
            }
        }
    }

    private fun purchasedItemCount(userId: String){
        viewModelScope.launch {
            getPurchasedItemsCountUseCase.invoke(userId).collect{resultState ->
                when(resultState){
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(purchasedCount = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun collectionItemCount(userId: String) {
        viewModelScope.launch {
            getCollectionsItemsCountUseCase.invoke(userId).collect{resultState ->
                when(resultState){
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(collectionCount = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun favoriteItemCount(userId: String) {
        viewModelScope.launch {
            getFavoritesItemsCountUseCase.invoke(userId).collect{resultState ->
                when(resultState){
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(favoriteCount = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getCurrentUserFromDatabase(userId:String){
        viewModelScope.launch {
            getCurrentUserFromDatabase.invoke(userId).collect{resultState ->
                when(resultState){
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(user = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

}