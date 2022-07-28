package com.example.e_commerce.presentation.favorite.collection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.databinding.FragmentMyCollectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyCollectionFragment : Fragment() {
    private var collectionBinding: FragmentMyCollectionBinding? = null
    private val viewModel: MyCollectionViewModel by viewModels()
    lateinit var myCollectionAdapter: MyCollectionAdapter

    @Inject
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        collectionBinding = FragmentMyCollectionBinding.inflate(inflater)

        return collectionBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initObservers()
    }

    private fun initObservers() {
        viewModel.handleEvent(MyCollectionUiEvent.GetAllCollections(userId))

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
              viewModel._uiState.collect{state ->
                 state.collections.let { flow ->
                   flow?.collect{list ->
                       myCollectionAdapter.differ.submitList(list)
                   }
                 }
              }
            }
        }
    }

    private fun initRecycler() {
        myCollectionAdapter = MyCollectionAdapter(
            object : CollectionDeleteClickListener {
                override fun collectionDelete(collection: Collection) {
                    deleteCollection(collection)
                }
            }
        )

        collectionBinding?.rvCollection?.apply {
            adapter = myCollectionAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun deleteCollection(collection: Collection) {
        viewModel.handleEvent(MyCollectionUiEvent.DeleteCollection(collection))
    }

    override fun onDestroy() {
        super.onDestroy()
        collectionBinding = null
    }
}