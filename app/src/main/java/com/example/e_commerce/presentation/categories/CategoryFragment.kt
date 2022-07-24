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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {
   private var categoryBinding:FragmentCategoryBinding?= null
    val viewModel : CategoryViewModel by viewModels()
    val args : CategoryFragmentArgs by navArgs()
    lateinit var categoryAdapter: CategoryAdapter

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

    private fun initObservers(){
        val categoryName = args.categoryName
        viewModel.handleEvent(CategoryUiEvent.GetCategoriesByName(categoryName))

        lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel._uiState.collect{state ->
                    state.products.let { categoryList ->
                        categoryAdapter.differ.submitList(categoryList)
                    }
                }
            }
        }
    }

    private fun initRecyclerView(){
        categoryAdapter = CategoryAdapter()

        categoryBinding?.rvCategory?.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        categoryBinding = null
    }
}