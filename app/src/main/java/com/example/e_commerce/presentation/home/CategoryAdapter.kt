package com.example.e_commerce.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.data.model.Category
import com.example.e_commerce.databinding.CategoryItemBinding

class CategoryAdapter(private val onCategoryClickListener: OnCategoryClickListener) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>(){

    class MyViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root){}

    val diffUtil = object:DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = differ.currentList[position]

        holder.binding.apply {
            categoryItemImage.setImageResource(list.categoryImage)
        }

        holder.itemView.setOnClickListener {
            onCategoryClickListener.categoryClickListener(list.categoryName)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}