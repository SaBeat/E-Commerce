package com.example.e_commerce.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_commerce.databinding.FragmentFavoriteBinding
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {
   private var favoriteBinding: FragmentFavoriteBinding? = null
   lateinit var favoriteViewPagerAdapter: FavoriteViewPagerAdapter

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      favoriteBinding = FragmentFavoriteBinding.inflate(inflater)

      return favoriteBinding!!.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      favoriteViewPagerAdapter = FavoriteViewPagerAdapter(this)
      favoriteBinding.let {
         it!!.favoritesViewPager.adapter = favoriteViewPagerAdapter
         TabLayoutMediator(it.favoritesTabLayout,it.favoritesViewPager){tab,pos ->
            when(pos){
               0 -> tab.text = "My Favorites"
               1 -> tab.text = "My Collections"
            }
         }.attach()
      }
   }

   override fun onDestroy() {
      super.onDestroy()
      favoriteBinding = null
   }
}