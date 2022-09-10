package com.example.e_commerce.presentation.basket

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
    private var basketBinding: FragmentBasketBinding? = null
    val viewModel: BasketViewModel by viewModels()
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

    private fun initObservers() {

        viewModel.handleEvent(BasketUiEvent.GetAllBasketItem(userId))
        viewModel.handleEvent(BasketUiEvent.GetBagBasketFromApi(userId))

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel._uiState.collect { state ->
                    state.basketItem.let { flowList ->
                        flowList?.collect { basketList ->
                            if (basketList.isNotEmpty()) {
                                basketBinding?.apply {
                                    rvBasket.visibility = View.VISIBLE
                                    cardDescription.visibility = View.VISIBLE
                                    imageEmptyBasket.visibility = View.INVISIBLE
                                    textSelectAddress.setOnClickListener {
                                        findNavController().navigate(R.id.action_basketFragment_to_mapsFragment)
                                    }
                                }

                            }
                            basketAdapter.differ.submitList(basketList)

                            basketBinding?.apply {
                                btnCheckout.setOnClickListener {
                                    basketList.forEach { list ->
                                        val purchased = Purchased(
                                            list.productName,
                                            list.productDescription,
                                            list.productCount,
                                            userId,
                                            list.productPrice,
                                            list.productImage,
                                            "Baki",
                                            list.productId
                                        )

                                        if (state.error == null) {
                                            Snackbar.make(
                                                requireView(),
                                                "Success",
                                                Snackbar.LENGTH_SHORT
                                            ).show()
                                            basketList.forEach { basket ->
                                                viewModel.handleEvent(
                                                    BasketUiEvent.DeleteBasketItem(
                                                        basket
                                                    )
                                                )
                                                viewModel.handleEvent(
                                                    BasketUiEvent.DeleteBasketItem(
                                                        list
                                                    )
                                                )
                                            }
                                        }

                                        viewModel.handleEvent(
                                            BasketUiEvent.InsertPurchasedToDatabase(
                                                purchased
                                            )
                                        )

                                    }
                                    var total = 0.0
                                    basketList.forEach { list ->

                                        val price = list.productPrice?.toDouble()
                                        price?.let { price ->
                                            total += price
                                        }
                                    }
                                    openProductCheckoutDialog(total)

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initRecycler() {
        basketAdapter = BasketAdapter(
            object : BasketDeleteClickListener {
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

    private fun openProductCheckoutDialog(price: Double) {
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.item_basket_checkout, null)
        val btn_checkout = view.findViewById<Button>(R.id.btn_checkout)
        val textPrice = view.findViewById<TextView>(R.id.text_price)
        builder.setView(view)

        textPrice?.text = price.toString()

        btn_checkout?.setOnClickListener {
            showSuccessDialog()

            builder.dismiss()
        }

        builder.setCanceledOnTouchOutside(false)
        builder.show()

    }

    private fun showSuccessDialog() {
        val layoutView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_splash, null, false)
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(layoutView)
        val alertDialog = dialogBuilder.create()

        val timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                alertDialog.show()
                alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            }

            override fun onFinish() {
                alertDialog.dismiss()
                Snackbar.make(requireView(), "Success Dialog", Snackbar.LENGTH_SHORT).show()
//            requireView().findNavController()
//               .navigate(androidx.work.R.id.successOrderFragment)
            }
        }
        timer.start()
    }

    private fun delete_Basket(basket: Basket) {
        viewModel.handleEvent(BasketUiEvent.DeleteBasketItem(basket))
    }

    override fun onDestroy() {
        super.onDestroy()
        basketBinding = null
    }
}