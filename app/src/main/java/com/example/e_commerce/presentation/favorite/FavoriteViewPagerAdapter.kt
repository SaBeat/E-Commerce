package com.example.e_commerce.presentation.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.e_commerce.presentation.favorite.collection.MyCollectionFragment
import com.example.e_commerce.presentation.favorite.favorite.MyFavoritesFragment

class FavoriteViewPagerAdapter(fragment: Fragment) :FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
       return when (position) {
            1 -> MyFavoritesFragment()
            2 -> MyCollectionFragment()
            else -> MyFavoritesFragment()
        }
    }
}