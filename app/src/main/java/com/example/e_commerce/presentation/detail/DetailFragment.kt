package com.example.e_commerce.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_commerce.R
import com.example.e_commerce.common.extensions.downloadToImageView
import com.example.e_commerce.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {
    private var detailBinding:FragmentDetailBinding?=null
    val args:DetailFragmentArgs by navArgs()

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
    }

    private fun initObservers(){
        val products = args.detail

        detailBinding?.apply {
            products.productImage?.let {
                imageDetail.downloadToImageView(it)
            }

            var count  = textInputPrice.text.toString().toInt()

            btnPlus.setOnClickListener {
                    count++
                textInputPrice.text = count.toString()
            }

            btnMinus.setOnClickListener {
                if(count>1){
                    count--
                    textInputPrice.text = count.toString()
                }else{
                    Snackbar.make(requireView(),"Miqdar sfirdir ve ya sifirdan kicikdir",Snackbar.LENGTH_SHORT).show()
                }
            }

            textDetailTitle.text = products.productTitle
            textDetailDescription.text = products.productDescription

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detailBinding = null
    }
}