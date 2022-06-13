package com.emptyslon.kode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emptyslon.kode.dataBase.Employee

class AdapterEmploees(var listCategories: List<Employee>) :
    RecyclerView.Adapter<AdapterEmploees.CategoriesHolder>() {

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


        fun bind(employee: Employee) {

            view.findViewById<TextView>(R.id.userName).text =
                "${employee.firstName} ${employee.lastName}"
            view.findViewById<TextView>(R.id.department).text = employee.department
            view.findViewById<TextView>(R.id.userTag).text = employee.userTag

            Glide.with(view.findViewById<ImageView>(R.id.avatar).context)
                .load(employee.avatarUrl)

                .circleCrop()
                .placeholder(R.drawable.ic_baseline_avatar)
//                .transform(newCircleTransform(post_image.getContext()))
//                .override(100,100)
                .error(R.drawable.ic_baseline_avatar)
                .into(view.findViewById<ImageView>(R.id.avatar));

        }


    }
}