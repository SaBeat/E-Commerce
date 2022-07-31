package com.example.e_commerce.presentation.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.R
import com.example.e_commerce.data.entities.product.Basket
import com.example.e_commerce.data.entities.product.Purchased
import com.example.e_commerce.databinding.FragmentBasketBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BasketFragment : Fragment() {
   private var basketBinding:FragmentBasketBinding?=null
   val viewModel:BasketViewModel by viewModels()
   lateinit var basketAdapter: BasketAdapter

   @Inject
   lateinit var userId: String

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
      initObservers()
      initRecycler()
   }

   private fun initObservers(){

      viewModel.handleEvent(BasketUiEvent.GetAllBasketItem(userId))
      viewModel.handleEvent(BasketUiEvent.GetBagBasketFromApi(userId))

      lifecycleScope.launch {
         viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel._uiState.collect{state ->
               state.basketItem.let { flowList ->
                  flowList?.collect{basketList ->
                     basketAdapter.differ.submitList(basketList)

                     basketBinding?.apply {
                        btnCheckOut.setOnClickListener {
                           basketList.forEach { list ->
                              val purchased = Purchased(
                                 list.productName,
                                 list.productCount,
                                 userId,
                                 list.productPrice,
                                 list.productImage,
                                 "Baki",
                                 list.productId
                              )

                              if(state.error==null){
                                 Snackbar.make(requireView(),"Success",Snackbar.LENGTH_SHORT).show()
                                 basketList.forEach { basket ->
                                    viewModel.handleEvent(BasketUiEvent.DeleteBasketItem(basket))
                                    viewModel.handleEvent(BasketUiEvent.DeleteBasketItem(list))
                                 }
                              }

                              viewModel.handleEvent(BasketUiEvent.InsertPurchasedToDatabase(purchased))
                           }

                        }
                     }
                  }
               }
            }
         }
      }
   }

   private fun initRecycler(){
      basketAdapter = BasketAdapter(
         object:BasketDeleteClickListener{
            override fun deleteBasket(basket: Basket) {
               delete_Basket(basket)
            }
         }
      )

      basketBinding?.rvBasket?.apply {
         adapter = basketAdapter
         layoutManager = LinearLayoutManager(requireContext())
      }
   }

   private fun openBottomSheetDialog(){
      val dialog = BottomSheetDialog(requireContext())
      dialog.setContentView(R.layout.item_basket_checkout)
      val textSelectAddress = dialog.findViewById<TextView>(R.id.text_select_address)
      val textAddressTitle = dialog.findViewById<TextView>(R.id.text_address_title)
      val textPrice = dialog.findViewById<TextView>(R.id.text_price)


   }

   private fun delete_Basket(basket: Basket){
      viewModel.handleEvent(BasketUiEvent.DeleteBasketItem(basket))
   }

   override fun onDestroy() {
      super.onDestroy()
      basketBinding = null
   }
}