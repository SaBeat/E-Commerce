package com.example.e_commerce.presentation.favorite.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.domain.usecase.local.product.GetFavoritesProductsFromDatabaseUseCase
import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.domain.usecase.local.product.DeleteFavoritesProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFavoritesViewModel @Inject constructor(
   private val getFavoritesProductsFromDatabaseUseCase: GetFavoritesProductsFromDatabaseUseCase,
   private val deleteFavoritesProductUseCase: DeleteFavoritesProductUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(MyFavoritesUiState())
    var _uiState:StateFlow<MyFavoritesUiState> = uiState.asStateFlow()

    fun handleEvent(event: MyFavoritesUiEvent){
        when(event){
            is MyFavoritesUiEvent.GetAllFavorites -> {
                getAllFavorites(event.userId)
            }

            is MyFavoritesUiEvent.DeleteFavorites -> {
                deleteFavorites(event.favorites)
            }
        }
    }

    fun getAllFavorites(userId:String){
        viewModelScope.launch {
            getFavoritesProductsFromDatabaseUseCase.invoke(userId).collect{resultState ->
                when(resultState){
                   is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(favorites = resultState.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun deleteFavorites(favorites: Favorites){
        viewModelScope.launch {
            deleteFavoritesProductUseCase.invoke(favorites).collect{resultState ->
                when(resultState){
                    is Resource.Error -> {
                        uiState.update { state ->
                            state.copy(error = resultState.message)
                        }
                    }
                    else -> {}
                }
            }
        }
    }


}