package com.example.e_commerce.presentation.forgot_password

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.common.Resource
import com.example.e_commerce.domain.usecase.firebase.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val forgotpasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(ForgotUiState())
    val _uiState: StateFlow<ForgotUiState> = uiState.asStateFlow()

    fun handleEvent(event: ForgotUiEvent){
        when(event){
            is ForgotUiEvent.ResetPassword ->{
                resetPassword(event.email)
            }
        }
    }

    private fun resetPassword(email: String) {
        viewModelScope.launch {
            forgotpasswordUseCase.invoke(email).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(success = true)
                        }
                    }
                    else -> {
                       Log.v("SABIT","RESET PASSWORD XETASI")
                    }
                }
            }
        }
    }

}