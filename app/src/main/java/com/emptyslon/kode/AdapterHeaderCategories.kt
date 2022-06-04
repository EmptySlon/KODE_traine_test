package com.emptyslon.kode

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emptyslon.kode.databinding.ItemCategoryBinding

class AdapterHeaderCategories(private val context: Context, private val listCategories: List<String>) : RecyclerView.Adapter<AdapterHeaderCategories.CategoriesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {

//        val binding = FoodItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
//        return FoodItemViewHolder(binding)
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false )
        return CategoriesHolder(binding)


    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        val categories = listCategories[position]
        holder.bind(categories)

    }

    override fun getItemCount(): Int = listCategories.size


    class CategoriesHolder(itemCategoriesBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemCategoriesBinding.root) {
        private val binding = itemCategoriesBinding

        fun bind(categories: String){
            binding.category.text = categories
        }


    }
}