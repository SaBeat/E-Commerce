package com.example.e_commerce.presentation.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.common.extensions.downloadToImageView
import com.example.e_commerce.data.entities.product.Basket
import com.example.e_commerce.databinding.BasketItemBinding

class BasketAdapter(
    private val basketDeleteClickListener: BasketDeleteClickListener
) : RecyclerView.Adapter<BasketAdapter.MyViewHolder>(){

    class MyViewHolder(val binding:BasketItemBinding):RecyclerView.ViewHolder(binding.root){}

    val diffUtil = object:DiffUtil.ItemCallback<Basket>(){
        override fun areItemsTheSame(oldItem: Basket, newItem: Basket): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Basket, newItem: Basket): Boolean {
            return true
        }
    }
    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(BasketItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = differ.currentList[position]

        holder.binding.apply {
            list.productImage?.let { imageBasket.downloadToImageView(it) }
            basketTitle.text = list.productName
            basketDescription.text = list.productDescription
            basketCount.text = list.productCount
            basketPrice.text = list.productPrice
        }
        holder.itemView.setOnClickListener {
            basketDeleteClickListener.deleteBasket(list)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}