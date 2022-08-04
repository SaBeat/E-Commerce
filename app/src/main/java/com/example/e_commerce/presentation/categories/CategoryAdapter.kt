package com.example.e_commerce.presentation.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.common.extensions.downloadToImageView
import com.example.e_commerce.data.entities.product.Product
import com.example.e_commerce.databinding.ProductItemBinding
import com.example.e_commerce.presentation.home.InsertProductToCollectionClickListener
import com.example.e_commerce.presentation.home.InsertProductToFavoriteClickListener
import com.example.e_commerce.presentation.home.OnProductClickListener
import com.example.e_commerce.presentation.home.ProductAdapter

class CategoryAdapter(
    private val insertProductToCollectionClickListener: InsertProductToCollectionClickListener,
    private val insertProductToFavoriteClickListener: InsertProductToFavoriteClickListener,
    private val productClickListener: OnProductClickListener
) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root){}

    val diffUtil = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        view.root.post {
            view.root.layoutParams.width = (parent.width/2.11).toInt()
            view.root.requestLayout()
        }

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = differ.currentList[position]

        holder.binding.apply {

            list.productImage?.let { imageProductItem.downloadToImageView(it) }
            textTitleProduct.text = list.productTitle
            textDescriptionProduct.text = list.productDescription
            textPriceProduct.text = "$${list.productPrice}"
        }

        var bool = false
        holder.binding.floatingActionButton1.setOnClickListener {
            bool =!bool
            if(bool){
                holder.binding.apply {
                    floatingActionButton2.visibility = View.VISIBLE
                    floatingActionButton3.visibility = View.VISIBLE
                }
            }else{
                holder.binding.apply {
                    floatingActionButton2.visibility = View.INVISIBLE
                    floatingActionButton3.visibility = View.INVISIBLE
                }
            }

        }

        holder.itemView.setOnClickListener {
            productClickListener.productClick(list)
        }

        holder.binding.floatingActionButton2.setOnClickListener {
            insertProductToFavoriteClickListener.insertFavorite(list)
        }

        holder.binding.floatingActionButton3.setOnClickListener {
            insertProductToCollectionClickListener.insertCollection(list)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}