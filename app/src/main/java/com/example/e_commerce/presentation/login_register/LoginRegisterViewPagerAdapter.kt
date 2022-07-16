package com.example.e_commerce.presentation.login_register

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.e_commerce.presentation.login_register.login.LoginFragment
import com.example.e_commerce.presentation.login_register.register.RegisterFragment

class LoginRegisterViewPagerAdapter(fragment: Fragment):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> LoginFragment()
            1-> RegisterFragment()
            else ->RegisterFragment()
        }
    }
}