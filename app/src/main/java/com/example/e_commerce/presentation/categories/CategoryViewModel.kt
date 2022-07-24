package com.example.e_commerce.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.common.Resource
import com.example.e_commerce.domain.usecase.remote.GetAllProductsByNameUseCase
import com.example.e_commerce.domain.usecase.remote.GetProductsByCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getProductsByCategoriesUseCase: GetProductsByCategoriesUseCase
) : ViewModel() {

    val uiState = MutableStateFlow(CategoryUiState())
    var _uiState:StateFlow<CategoryUiState> = uiState.asStateFlow()

    fun handleEvent(categoryUiEvent: CategoryUiEvent){
        when(categoryUiEvent){
            is CategoryUiEvent.GetCategoriesByName -> {
                getAllCategoriesByName(categoryUiEvent.categoryName)
            }
        }
    }

    fun getAllCategoriesByName(categoryName:String){
        viewModelScope.launch {
            getProductsByCategoriesUseCase.invoke(categoryName).collect{resultState ->
                when(resultState){
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(products = resultState.data)
                        }
                    }
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