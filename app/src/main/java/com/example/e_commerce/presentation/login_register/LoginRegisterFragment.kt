package com.example.e_commerce.presentation.login_register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentLoginRegisterBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginRegisterFragment : Fragment() {

    private var loginRegisterBinding:FragmentLoginRegisterBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginRegisterBinding = FragmentLoginRegisterBinding.inflate(inflater)


        return loginRegisterBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = LoginRegisterViewPagerAdapter(this)

        loginRegisterBinding.let {
            it?.loginRegisterViewpager?.adapter = adapter
            TabLayoutMediator(
                it!!.loginRegisterTabLayout,it.loginRegisterViewpager
            ){tab,pos->
                when(pos){
                    0 -> tab.text = "Login"
                    1 -> tab.text = "Register"
                }
            }.attach()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        loginRegisterBinding = null
    }

}