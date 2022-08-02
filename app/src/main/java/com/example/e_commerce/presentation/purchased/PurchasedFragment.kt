package com.example.e_commerce.presentation.purchased

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
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentPurchasedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PurchasedFragment : Fragment() {
  private var purchaedBinding : FragmentPurchasedBinding?=null
  lateinit var purchasedAdapter: PurchasedAdapter
  val viewModel : PurchasedViewModel by viewModels()

    @Inject
    lateinit var userId:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        purchaedBinding = FragmentPurchasedBinding.inflate(inflater)

        return purchaedBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initObservers()
    }

    private fun initObservers(){
        viewModel.handleEvent(PurchasedUiEvent.GetPurchasedProducts(userId))

        lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel._uiState.collect{state ->
                    state.purchased.let { flowList ->
                        flowList?.collect{purchasedList ->
                            purchasedAdapter.differ.submitList(purchasedList)
                        }
                    }
                }
            }
        }
    }

    private fun initRecycler(){
        purchasedAdapter = PurchasedAdapter()

        purchaedBinding?.rvPurchased?.apply {
            adapter = purchasedAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        purchaedBinding = null
    }
}