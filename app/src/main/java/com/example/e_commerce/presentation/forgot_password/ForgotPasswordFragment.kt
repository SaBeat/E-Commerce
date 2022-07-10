package com.example.e_commerce.presentation.forgot_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ActivityMainBinding
import com.example.e_commerce.databinding.FragmentForgotPasswordBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private var forgotBinding: FragmentForgotPasswordBinding? = null
    private val forgotViewModel: ForgotViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        forgotBinding = FragmentForgotPasswordBinding.inflate(inflater)

        return forgotBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners(){
        forgotBinding?.btnResetPassword?.setOnClickListener {
            val email = forgotBinding?.etForgotEmail?.text.toString().trim()
            if(email.isEmpty()){
                forgotBinding?.etForgotEmail?.error = "Email is empty"
            }else{
                Snackbar.make(requireView(),"Reset Password",Snackbar.LENGTH_SHORT).show()
                forgotViewModel.handleEvent(ForgotUiEvent.ResetPassword(email))
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        forgotBinding = null
    }
}