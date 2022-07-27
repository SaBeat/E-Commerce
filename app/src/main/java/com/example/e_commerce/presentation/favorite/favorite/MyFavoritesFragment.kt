package com.example.e_commerce.presentation.favorite.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.R
import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.databinding.FragmentMyFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyFavoritesFragment : Fragment() {
   private var favoritesBinding:FragmentMyFavoritesBinding?=null
   val viewModel:MyFavoritesViewModel by viewModels()
   lateinit var myFavoritesAdapter: MyFavoritesAdapter

    @Inject
    lateinit var userId: String

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
        initRecyclerView()
        initObservers()
    }

    private fun initObservers(){
        viewModel.handleEvent(MyFavoritesUiEvent.GetAllFavorites(userId))

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel._uiState.collect{state ->
                    state.favorites.let { listFav ->
                        listFav?.collect{
                            myFavoritesAdapter.differ.submitList(it)
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView(){
        myFavoritesAdapter = MyFavoritesAdapter(
            object:FavoriteDeleteClickListener{
                override fun favoriteDelete(favorites: Favorites) {
                    favoritesDelete(favorites)
                }
            }
        )

        favoritesBinding?.rvMyFavorites?.apply {
            adapter = myFavoritesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun favoritesDelete(favorites: Favorites){
        viewModel.handleEvent(MyFavoritesUiEvent.DeleteFavorites(favorites))
    }

    override fun onDestroy() {
        super.onDestroy()
        favoritesBinding = null
    }
}