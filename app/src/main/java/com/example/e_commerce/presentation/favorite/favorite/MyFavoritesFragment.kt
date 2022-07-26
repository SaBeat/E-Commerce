package com.example.e_commerce.presentation.favorite.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentMyFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFavoritesFragment : Fragment() {
   private var favoritesBinding:FragmentMyFavoritesBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoritesBinding = FragmentMyFavoritesBinding.inflate(inflater)

        return favoritesBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        favoritesBinding = null
    }
}