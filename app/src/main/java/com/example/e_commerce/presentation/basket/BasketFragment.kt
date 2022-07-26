package com.example.e_commerce.presentation.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_commerce.databinding.FragmentBasketBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : Fragment() {
   private var basketBinding:FragmentBasketBinding?=null

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      basketBinding = FragmentBasketBinding.inflate(layoutInflater)

      return basketBinding!!.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
   }

   override fun onDestroy() {
      super.onDestroy()
      basketBinding = null
   }
}