package com.example.e_commerce.presentation.detail

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_commerce.R
import com.example.e_commerce.common.extensions.downloadToImageView
import com.example.e_commerce.data.entities.product.Basket
import com.example.e_commerce.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var detailBinding: FragmentDetailBinding? = null
    val args: DetailFragmentArgs by navArgs()
    val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailBinding = FragmentDetailBinding.inflate(inflater)

        return detailBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        detailBinding?.btnBack?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initObservers() {
        val products = args.detail

        detailBinding?.apply {
            products.productImage?.let {
                imageDetail.downloadToImageView(it)
            }

            var count = textInputPrice.text.toString().toInt()

            btnPlus.setOnClickListener {
                count++
                textInputPrice.text = count.toString()
            }

            btnMinus.setOnClickListener {
                if (count > 1) {
                    count--
                    textInputPrice.text = count.toString()
                } else {
                    Snackbar.make(
                        requireView(),
                        "Miqdar sfirdir ve ya sifirdan kicikdir",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            textDetailTitle.text = products.productTitle
            textDetailDescription.text = products.productDescription
            textDetailPrice.text = "$${products.productPrice}"

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnAddCart.setOnClickListener {
                val itemCount = count.toString()
                val price = products.productPrice?.toFloat()
                val basket = Basket(
                    products.productTitle,
                    products.productDescription,
                    itemCount,
                    userId,
                    (count * price!!).toString(),
                    products.productImage
                )

                viewModel.handleEvent(
                    DetailUiEvent.AddProductToBasket(
                        userId,
                        products.productTitle,
                        (count * price!!).toDouble(),
                        products.productDescription,
                        products.productCategory,
                        products.productImage,
                        0.0,
                        count,
                        1
                    )
                )

                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel._uiState.collect { state ->
                            state.response.let { response ->
                                if (response?.status == 1) {
                                    Snackbar.make(
                                        requireView(),
                                        "Successfully added to Basket",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
                viewModel.handleEvent(DetailUiEvent.InsertProductToBasket(basket))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detailBinding = null
    }
}