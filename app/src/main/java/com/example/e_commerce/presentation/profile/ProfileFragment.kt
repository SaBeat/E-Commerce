package com.example.e_commerce.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var profileBinding:FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding = FragmentProfileBinding.inflate(inflater)


        return profileBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileBinding?.btnGoPurchased?.setOnClickListener {
            findNavController().navigate(R.id.purchasedFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        profileBinding = null
    }
}