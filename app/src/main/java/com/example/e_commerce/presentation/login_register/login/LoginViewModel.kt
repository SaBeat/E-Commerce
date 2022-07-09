package com.example.e_commerce.presentation.login_register.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.model.AuthModel
import com.example.e_commerce.domain.usecase.firebase.GetCurrentUserIdUseCase
import com.example.e_commerce.domain.usecase.firebase.LogInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val getCurrentUserUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(LoginUiState())
    val _uiState: StateFlow<LoginUiState> = uiState.asStateFlow()

    fun handleEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {
            is LoginUiEvent.Login -> {
                login(loginUiEvent.authModel)
            }

            is LoginUiEvent.GetCurrentUser -> {
                getCurrentUser()
            }
        }
    }

    fun login(authModel: AuthModel) {
        viewModelScope.launch {
            logInUseCase.invoke(authModel).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(isLoggedIn = true)
                        }
                    }

                    else -> {
                        Log.v("SABIT", "Login Error")
                    }
                }
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(currentUser = result.data)
                        }
                    }
                    else -> {
                        Log.v("SABIT", "Login GetCurrentUser Error")
                    }
                }
            }
        }
    }


}