package com.example.e_commerce.presentation.home

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.common.extensions.downloadToImageView
import com.example.e_commerce.data.entities.product.Product
import com.example.e_commerce.databinding.ProductItemBinding

class ProductAdapter(
    private val productClickListener: OnProductClickListener,
    private val insertProductToFavoriteClickListener: InsertProductToFavoriteClickListener,
    private val insertProductToCollectionClickListener: InsertProductToCollectionClickListener
) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    val diffUtil = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        view.root.post {
//            view.root.layoutParams.width = parent.width/2
//            view.root.requestLayout()
//        }

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = differ.currentList[position]

        val rotateOpen: Animation by lazy {
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.rotate_open_anim
            )
        }
        val rotateClose: Animation by lazy {
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.rotate_close_anim
            )
        }
        val fromBottom: Animation by lazy {
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.from_bottom_anim
            )
        }
        val toBottom: Animation by lazy {
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.to_bottom_anim
            )
        }

        holder.binding.apply {

            list.productImage?.let { imageProductItem.downloadToImageView(it) }
            textTitleProduct.text = list.productTitle
            textDescriptionProduct.text = list.productDescription
            textPriceProduct.text = "$${list.productPrice}"

            var clicked = false
            floatingActionButton1.setOnClickListener {
                clicked = !clicked
                if (clicked) {
                    floatingActionButton1.startAnimation(rotateOpen)
                    floatingActionButton2.startAnimation(fromBottom)
                    floatingActionButton3.startAnimation(fromBottom)

                } else {
                    floatingActionButton1.startAnimation(rotateClose)
                    floatingActionButton2.startAnimation(toBottom)
                    floatingActionButton3.startAnimation(toBottom)
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