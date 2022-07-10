package com.example.e_commerce.presentation.login_register.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.data.model.AuthModel
import com.example.e_commerce.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var loginBinding: FragmentLoginBinding? = null
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginBinding = FragmentLoginBinding.inflate(inflater)

        return loginBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

    }

    private fun initListeners() {
        loginBinding?.apply {
            btnLogIn.setOnClickListener {
                val email = etLoginEmail.text.toString().trim()
                val password = etLoginPassword.text.toString().trim()

                if (email.isEmpty()) {
                    etLoginEmail.error = "Email chould not empty"
                }
                else if (password.isEmpty()) {
                    etLoginPassword.error = "Password chould not empty"
                }else {
                    val auth = AuthModel(email, password)
                    loginViewModel.handleEvent(LoginUiEvent.Login(auth))
                    lifecycleScope.launch {
                        loginViewModel._uiState.collect { state ->
                            state.isLoggedIn.let { loggedIn ->
                                if (loggedIn == true) {
                                    Snackbar.make(
                                        requireView(),
                                        "SUCCESS_LOGIN",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                    findNavController().navigate(R.id.homeFragment)
                                } else {

                                }
                            }
                        }
                    }
                }

                val sharedPref =
                    activity?.getSharedPreferences(
                        "getSharedPref",
                        Context.MODE_PRIVATE
                    )
                with(sharedPref?.edit()) {
                    this?.putString("currentUser", email)
                    this?.apply()
                }

            }

            txtLoginForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.forgotPasswordFragment)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        loginBinding = null
    }
}