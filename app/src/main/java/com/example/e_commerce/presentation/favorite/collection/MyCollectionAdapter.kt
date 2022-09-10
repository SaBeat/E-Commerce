package com.example.e_commerce.presentation.favorite.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.common.extensions.downloadToImageView
import com.example.e_commerce.data.entities.product.Collection
import com.example.e_commerce.databinding.MycollectionItemBinding

class MyCollectionAdapter(
    private val collectionDeleteClickListener: CollectionDeleteClickListener
) : RecyclerView.Adapter<MyCollectionAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: MycollectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    val diffUtil = object : DiffUtil.ItemCallback<Collection>() {
        override fun areItemsTheSame(oldItem: Collection, newItem: Collection): Boolean {
            return oldItem.productId==newItem.productId
        }

        override fun areContentsTheSame(oldItem: Collection, newItem: Collection): Boolean {
            return oldItem==newItem
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

            btnDelete.setOnClickListener {
                collectionDeleteClickListener.collectionDelete(list)
            }
        }

    }

    override fun getItemCount(): Int = differ.currentList.size
}