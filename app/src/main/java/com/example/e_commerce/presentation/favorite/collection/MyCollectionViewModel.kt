package com.example.e_commerce.presentation.favorite.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.domain.usecase.local.product.GetCollectionProductsFromDatabaseUseCase
import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.domain.usecase.local.product.DeleteCollectionProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCollectionViewModel @Inject constructor(
  private val getCollectionProductsFromDatabaseUseCase: GetCollectionProductsFromDatabaseUseCase,
  private val deleteCollectionProductUseCase: DeleteCollectionProductUseCase,
) : ViewModel(){

    val uiState = MutableStateFlow(MyCollectionUiState())
    var _uiState: StateFlow<MyCollectionUiState> = uiState.asStateFlow()

    fun handleEvent(event:MyCollectionUiEvent){
        when(event){
            is MyCollectionUiEvent.GetAllCollections -> {
                getAllCollection(event.userId)
            }
            is MyCollectionUiEvent.DeleteCollection -> {
                deleteCollection(event.collection)
            }
        }
    }

    fun getAllCollection(userId:String){
        viewModelScope.launch {
            getCollectionProductsFromDatabaseUseCase.invoke(userId).collect{resultState ->
                when(resultState){
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(collections = resultState.data)
                        }
                    }
                    is Resource.Error ->{
                        uiState.update { state ->
                            state.copy(error = resultState.message)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun deleteCollection(collection: Collection){
        viewModelScope.launch {
            deleteCollectionProductUseCase.invoke(collection).collect{resultState ->
                when(resultState){
                    is Resource.Error ->{
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