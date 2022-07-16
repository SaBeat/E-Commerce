package com.example.e_commerce.presentation.login_register.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.common.Resource
import com.example.e_commerce.data.entities.user.User
import com.example.e_commerce.data.model.AuthModel
import com.example.e_commerce.domain.usecase.firebase.CreateUserUseCase
import com.example.e_commerce.domain.usecase.local.user.InsertUserToDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val insertUserToDatabaseUseCase: InsertUserToDatabaseUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(RegisterUiState())
    val _uiState: StateFlow<RegisterUiState> = uiState.asStateFlow()

    fun handleEvent(event:RegisterUiEvent){
        when(event){
            is RegisterUiEvent.CreatUser ->{
                creatUser(event.authModel)
            }
            is RegisterUiEvent.InsertuserToDatabase ->{
                insertUserToDatabase(event.user)
            }
        }
    }

    fun creatUser(authModel: AuthModel){
        viewModelScope.launch {
            createUserUseCase.invoke(authModel).collect{resultState->
                when(resultState){
                    is Resource.Success ->{
                        uiState.update {state->
                            state.copy(isRegister = true)
                        }
                    }
                    else->{}
                }
            }
        }
    }

    fun insertUserToDatabase(user: User){
        viewModelScope.launch {
            insertUserToDatabaseUseCase.invoke(user).collect{resultState->
                when(resultState){
                    is Resource.Error->{
                        uiState.update {state->
                            state.copy(error = resultState.message)
                        }
                    }
                    else->{}
                }
            }
        }
    }

}