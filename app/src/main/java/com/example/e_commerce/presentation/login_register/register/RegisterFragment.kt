package com.example.e_commerce.presentation.login_register.register

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.e_commerce.data.entities.user.User
import com.example.e_commerce.data.model.AuthModel
import com.example.e_commerce.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var registerBinding: FragmentRegisterBinding? = null
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerBinding = FragmentRegisterBinding.inflate(inflater)

        return registerBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        registerBinding?.apply {
            btnRegister.setOnClickListener {
                val username = etRegisterUsername.text.toString().trim()
                val email = etRegisterEmail.text.toString().trim()
                val password = etRegisterPassword.text.toString().trim()

                if (username.isEmpty()) {
                    etRegisterUsername.error = "Username is empty"
                } else if (email.isEmpty()) {
                    etRegisterEmail.error = "Email is empty"
                } else if (password.isEmpty()) {
                    etRegisterPassword.error = "Password is empty"
                } else {

                    val sharedPref =
                        activity?.getSharedPreferences(
                            "getSharedPref",
                            Context.MODE_PRIVATE
                        )
                    with(sharedPref?.edit()) {
                        this?.putString("currentUser", username)
                        this?.apply()
                    }

                    val authModel = AuthModel(email, password)
                    val user = User(username, email, password, email)
                    registerViewModel.handleEvent(RegisterUiEvent.CreatUser(authModel))
                    lifecycleScope.launch {
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            registerViewModel._uiState.collect { state ->
                                state.isRegister.let { isRegister ->
                                    if (isRegister != true) {

                                    } else {
                                        registerViewModel.handleEvent(
                                            RegisterUiEvent.InsertuserToDatabase(
                                                user
                                            )
                                        )
                                        state.dbError.let { dbError ->
                                            if (dbError.isNullOrBlank()) {
                                                Snackbar.make(
                                                    requireView(), "Succesflly register",
                                                    Snackbar.LENGTH_SHORT
                                                ).show()

                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        registerBinding = null
    }
}