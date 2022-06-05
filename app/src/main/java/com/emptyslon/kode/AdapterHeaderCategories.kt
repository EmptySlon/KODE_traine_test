package com.emptyslon.kode

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emptyslon.kode.databinding.ItemCategoryBinding

class AdapterHeaderCategories(private val listCategories: List<String>) : RecyclerView.Adapter<AdapterHeaderCategories.CategoriesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {

//        val binding = FoodItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
//        return FoodItemViewHolder(binding)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return CategoriesHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        val categories = listCategories[position]
        holder.bind(categories)

    }

    override fun getItemCount(): Int = listCategories.size


    class CategoriesHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {


        fun bind(categories: String){

           view.findViewById<TextView>(R.id.nameCategory).text = categories
        }


    }
}