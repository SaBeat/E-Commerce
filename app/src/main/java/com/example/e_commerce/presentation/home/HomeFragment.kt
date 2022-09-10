package com.example.e_commerce.presentation.home

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.e_commerce.R
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.data.entities.product.Product
import com.example.e_commerce.data.model.Category
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.relex.circleindicator.CircleIndicator
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
   private var homeBinding:FragmentHomeBinding? = null
   private val viewModel:HomeViewModel by viewModels()
   lateinit var categoryAdapter: CategoryAdapter
   lateinit var productAdapter: ProductAdapter
   lateinit var campaignsAdapter: CampaignsAdapter

    @Inject
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater)

        return homeBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(FirebaseAuth.getInstance().currentUser==null){
            findNavController().navigate(R.id.loginRegisterFragment)
        }

        initRecyclerView()
        initObservers()

    }

    private fun initObservers(){
        val user = "suveybesenakucuk"
        val discount = "Discount"
        viewModel.handleEvent(HomeUIEvent.GetCategories(user))
        viewModel.handleEvent(HomeUIEvent.GetAllProductsFromDatabase)
        viewModel.handleEvent(HomeUIEvent.GetDiscountProducts(discount))

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel._uiState.collect{state ->
                    state.getProductsFromDatabase.let{discountList ->
                        discountList?.collect{productList ->
                            productAdapter.differ.submitList(productList)
                        }

                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel._uiState.collect{state ->
                    state.allProducts.let{discountList ->
                        campaignsAdapter.differ.submitList(discountList)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel._uiState.collect{state ->
                    state.getCategories.let { categoryList ->
                        Log.v("SABIT",categoryList?.size.toString())
                    }
                }
            }
        }

        val categoryList = arrayListOf<Category>()
        val electronic = Category(
            "Electronic",
            R.drawable.electronic
        )
        val blues = Category(
            "Blues",
            R.drawable.blues
        )
        val pop = Category(
            "Pop",
            R.drawable.pop
        )
        val classical = Category(
            "Classical",
            R.drawable.classical
        )
        val funk = Category(
            "Funk",
            R.drawable.funk
        )
        val rock = Category(
            "Rock",
            R.drawable.rock
        )
        val jazz = Category(
            "Jazz",
            R.drawable.jazz
        )

        categoryList.add(electronic)
        categoryList.add(pop)
        categoryList.add(funk)
        categoryList.add(classical)
        categoryList.add(rock)
        categoryList.add(blues)
        categoryList.add(jazz)

        categoryAdapter.differ.submitList(categoryList)
    }

    private fun initRecyclerView(){
        productAdapter = ProductAdapter(object: OnProductClickListener{
            override fun productClick(product: Product) {
                goDetailFragment(product)
            }
        },object:InsertProductToFavoriteClickListener{
            override fun insertFavorite(product: Product) {
                insertProductFavorite(product)
            }
        },object:InsertProductToCollectionClickListener{
            override fun insertCollection(product: Product) {
                insertProductCollection(product)
            }
        }
        )
        campaignsAdapter = CampaignsAdapter(
            object:OnCampaignClickListener{
                override fun clickCampaign(product: Product) {
                    goDetailFragment(product)
                }
            }
        )

        categoryAdapter = CategoryAdapter(object : OnCategoryClickListener{
            override fun categoryClickListener(categoryName: String) {
                goCategoryFragment(categoryName)
            }
        })

        homeBinding?.carouselRecyclerview?.apply {
            adapter = categoryAdapter
            set3DItem(true)
            setInfinite(true)
            setAlpha(true)
            setPadding(5, 0, 5, 0)
        }

        homeBinding?.rvProduct?.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false)
        }

        homeBinding?.rvDiscounts?.apply {
            adapter = campaignsAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun goCategoryFragment(categoryName:String){
        val bundle = Bundle().apply {
            putString("category",categoryName)
        }
        findNavController().navigate(
            R.id.action_homeFragment_to_categoryFragment,
            bundle
        )
    }

    private fun goDetailFragment(product : Product){
        val bundle = Bundle().apply {
            putParcelable("detail",product)
        }

        findNavController().navigate(
            R.id.action_homeFragment_to_detailFragment,
            bundle
        )
    }

    private fun insertProductFavorite(product: Product){
        val favorites = Favorites(
            product.productTitle,
            product.productDescription,
            userId,
            product.productPrice,
            product.productImage,
            product.productId
        )

        viewModel.handleEvent(HomeUIEvent.InsertProductToFavorites(favorites))
        Snackbar.make(requireView(),"Insert database favorites",Snackbar.LENGTH_SHORT).show()
    }

    private fun insertProductCollection(product: Product){
        val collections = Collection(
            product.productTitle,
            product.productDescription,
            userId,
            product.productPrice,
            product.productImage,
            product.productId
        )
        viewModel.handleEvent(HomeUIEvent.InsertProductToCollection(collections))
        Snackbar.make(requireView(),"Insert database collection",Snackbar.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }
}