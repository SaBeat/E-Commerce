package com.example.e_commerce

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var splashBinding : FragmentSplashBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        splashBinding = FragmentSplashBinding.inflate(inflater)

        return splashBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPref = activity?.getSharedPreferences(
            "getSharedPref", Context.MODE_PRIVATE
        )
        val pref = sharedPref?.getString("currentUser", null)
        Handler(Looper.myLooper()!!).postDelayed({
            if(pref!=null){
                findNavController().navigate(R.id.homeFragment)
            }else{
                findNavController().navigate(R.id.loginRegisterFragment)
            }
//            findNavController().navigate(R.id.loginRegisterFragment)

        },2000)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        splashBinding = null
    }
}