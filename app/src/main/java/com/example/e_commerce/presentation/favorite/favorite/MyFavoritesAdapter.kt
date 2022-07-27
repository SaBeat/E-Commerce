package com.example.e_commerce.presentation.favorite.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.common.extensions.downloadToImageView
import com.example.e_commerce.data.entities.product.Favorites
import com.example.e_commerce.databinding.MyfavoritesItemBinding

class MyFavoritesAdapter(private val onFavoritesListToDeleteClickHandler: FavoriteDeleteClickListener) :
    RecyclerView.Adapter<MyFavoritesAdapter.FavoritesVH>() {
    class FavoritesVH(val binding: MyfavoritesItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Favorites>() {
        override fun areItemsTheSame(
            oldItem: Favorites,
            newItem: Favorites
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: Favorites,
            newItem: Favorites
        ): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH {
        val binding =
            MyfavoritesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesVH(binding)
    }

    override fun onBindViewHolder(holder: FavoritesVH, position: Int) {
        val product = differ.currentList[position]
        holder.binding.apply {
            product.productImage?.let { image ->
                imageFavorite.downloadToImageView(image)
            }
            textTitle.text = product.productName
            textPrice.text = "$${product.productPrice}"

            btnDelete.setOnClickListener {
                onFavoritesListToDeleteClickHandler.favoriteDelete(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}