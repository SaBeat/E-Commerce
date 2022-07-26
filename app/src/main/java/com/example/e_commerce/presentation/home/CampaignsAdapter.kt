package com.example.e_commerce.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.common.extensions.downloadToImageView
import com.example.e_commerce.data.entities.product.Product
import com.example.e_commerce.databinding.ItemCampaignsBinding

class CampaignsAdapter(private val campaignClickListener: OnCampaignClickListener) : RecyclerView.Adapter<CampaignsAdapter.MyViewHolder>() {

    val diffUtil = object:DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return true
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)


    class MyViewHolder(val binding:ItemCampaignsBinding):RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCampaignsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            list.productImage.let {
                ivCampaigns.downloadToImageView(it.toString())
            }
        }
        holder.itemView.setOnClickListener {
            campaignClickListener.clickCampaign(list)
        }

    }

    override fun getItemCount(): Int = differ.currentList.size
}