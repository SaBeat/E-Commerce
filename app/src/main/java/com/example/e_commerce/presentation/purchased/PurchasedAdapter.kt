package com.example.e_commerce.presentation.purchased

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.common.extensions.downloadToImageView
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.data.entities.product.Purchased
import com.example.e_commerce.databinding.MycollectionItemBinding


class PurchasedAdapter : RecyclerView.Adapter<PurchasedAdapter.MyViewHolder>(){

    class MyViewHolder(val binding: MycollectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    val diffUtil = object : DiffUtil.ItemCallback<Purchased>() {
        override fun areItemsTheSame(oldItem: Purchased, newItem: Purchased): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Purchased, newItem: Purchased): Boolean {
            return true
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MycollectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = differ.currentList[position]

        holder.binding.apply {
            list.productImage?.let {
                imageCollection.downloadToImageView(it)
            }
            textTitle.text = list.productName
            textDescription.text = list.productDescription
            textPrice.text = "$${list.productPrice}"
        }

    }

    override fun getItemCount(): Int = differ.currentList.size

}