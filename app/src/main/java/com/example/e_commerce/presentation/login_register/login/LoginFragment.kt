package com.example.e_commerce.presentation.login_register.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.common.extensions.snack
import com.example.e_commerce.data.model.AuthModel
import com.example.e_commerce.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var loginBinding: FragmentLoginBinding? = null
    private val loginViewModel: LoginViewModel by viewModels()
    private val VALID_EMAIL_ADDRESS_REGEX: Pattern =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

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
        loginViewModel.handleEvent(LoginUiEvent.GetAllProducts)
        loginBinding?.apply {
            btnLogIn.setOnClickListener {
                val email = etLoginEmail.text.toString().trim()
                val password = etLoginPassword.text.toString().trim()

                if (!validate(email)) {
                    etLoginEmail.error = "Email is invalid"
                }
                if (email.isEmpty()) {
                    etLoginEmail.error = "Email chould not empty"
                } else if (password.isEmpty()) {
                    etLoginPassword.error = "Password chould not empty"
                } else {
                    val authModel = AuthModel(email, password)
                    loginViewModel.handleEvent(LoginUiEvent.Login(authModel))
                    lifecycleScope.launch {
                        loginViewModel._uiState.collect { state ->
                            state.isLoggedIn.let { loggedIn ->
                                if (loggedIn == true) {

                                    view?.snack("Log in",Snackbar.LENGTH_SHORT){}
                                    findNavController().navigate(R.id.homeFragment)

                                    //Get All Products from Database
                                    getAllDataFromDatabase()

                                } else {
                                   view?.snack("Email or Password is incorrect",Snackbar.LENGTH_SHORT){}
                                }
                            }
                        }
                    }
                }

                //Insert user to haredPreference
                insertUserToSharedPreferences(email)
            }

            txtLoginForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.forgotPasswordFragment)
            }
        }
    }

    private suspend fun getAllDataFromDatabase() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel._uiState.collect { state ->
                    state.products.let { products ->
                        loginViewModel.handleEvent(
                            LoginUiEvent.InsertProductToDatabase(
                                products ?: mutableListOf()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun insertUserToSharedPreferences(email: String) {
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

    private fun validate(emailStr: CharSequence): Boolean {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
        return matcher.find()
    }

    override fun onDestroy() {
        super.onDestroy()
        loginBinding = null
    }
}