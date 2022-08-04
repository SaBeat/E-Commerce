package com.example.e_commerce.presentation.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commerce.R
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.data.entities.product.Product
import com.example.e_commerce.databinding.FragmentCategoryBinding
import com.example.e_commerce.presentation.home.InsertProductToCollectionClickListener
import com.example.e_commerce.presentation.home.InsertProductToFavoriteClickListener
import com.example.e_commerce.presentation.home.OnProductClickListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private var categoryBinding: FragmentCategoryBinding? = null
    val viewModel: CategoryViewModel by viewModels()
    val args: CategoryFragmentArgs by navArgs()
    lateinit var categoryAdapter: CategoryAdapter

    @Inject
    lateinit var userId:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoryBinding = FragmentCategoryBinding.inflate(inflater)


        return categoryBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
    }

    private fun initObservers() {
        val categoryName = args.category
        viewModel.handleEvent(CategoryUiEvent.GetCategoriesByName(categoryName))

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel._uiState.collect { state ->
                    state.products.let { categoryList ->
                        categoryAdapter.differ.submitList(categoryList)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        categoryBinding?.titleCategory?.text = args.category
        categoryAdapter = CategoryAdapter(object : InsertProductToCollectionClickListener {
            override fun insertCollection(product: Product) {
                collcetionInsert(product)
            }
        }, object : InsertProductToFavoriteClickListener {
            override fun insertFavorite(product: Product) {
                favoriteInsert(product)
            }
        }, object : OnProductClickListener {
            override fun productClick(product: Product) {
                goDetailFragment(product)
            }
        }
        )

        categoryBinding?.rvCategory?.apply {
            adapter = categoryAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }

        categoryBinding?.btnBack?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun collcetionInsert(product: Product){
        val collection = Collection(
            product.productTitle,
            product.productDescription,
            userId,
            product.productPrice,
            product.productImage,
            product.productId
        )
        viewModel.handleEvent(CategoryUiEvent.InsertProductToCollection(collection))
        Snackbar.make(requireView(),"Successfully added to Collection",Snackbar.LENGTH_SHORT).show()
    }

    private fun favoriteInsert(product: Product){
        val favorite = Favorites(
            product.productTitle,
            product.productDescription,
            userId,
            product.productPrice,
            product.productImage,
            product.productId
        )
        viewModel.handleEvent(CategoryUiEvent.InsertProductToFavorite(favorite))
        Snackbar.make(requireView(),"Successfully added to Favorite",Snackbar.LENGTH_SHORT).show()
    }

    private fun goDetailFragment(product: Product){
        val bundle = Bundle().apply{
            putParcelable("detail",product)
        }
        findNavController().navigate(R.id.detailFragment,bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        categoryBinding = null
    }
}