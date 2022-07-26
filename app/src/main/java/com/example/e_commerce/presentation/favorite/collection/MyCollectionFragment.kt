package com.example.e_commerce.presentation.favorite.collection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentMyCollectionBinding

class MyCollectionFragment : Fragment() {
  private var collectionBinding:FragmentMyCollectionBinding?=null

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
  }

  override fun onDestroy() {
    super.onDestroy()
    collectionBinding = null
  }
}