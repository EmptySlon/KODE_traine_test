package com.emptyslon.kode

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emptyslon.kode.common.Common
import com.emptyslon.kode.dataBase.Employee
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AdapterEmploees(
    var listEmployees: List<Employee>,
    private val filterDepartment: String = "all"
) :
    RecyclerView.Adapter<AdapterEmploees.CategoriesHolder>() {

    init {
        if (filterDepartment != "all")
            listEmployees = listEmployees.filter { it.department.lowercase() == filterDepartment }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return CategoriesHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        val employee = listEmployees[position]
        holder.bind(employee)
    }

    override fun getItemCount(): Int = listEmployees.size


    class CategoriesHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {



        fun bind(employee: Employee) {
            if (Common.typeSorted == view.context.getString(R.string.sorted_birthday) ) {
                view.findViewById<TextView>(R.id.birthday).visibility = View.VISIBLE



                val date = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd")
                    .parse(employee.birthday)
                val desiredFormat = DateTimeFormatter
                    .ofPattern("dd MMM",Locale("ru"))
                    .format(date)

//                val date = SimpleDateFormat("yyyy-MM-dd").parse(employee.birthday)
//                val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
//                val birthday = dateFormatter.format(date!!)
//                val birthday =
//                    LocalDate.parse( employee.birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd") )
//                        .let { "${it.dayOfMonth} ${it.month}" }

                view.findViewById<TextView>(R.id.birthday).text = desiredFormat
            }
            view.findViewById<TextView>(R.id.userName).text =
                "${employee.firstName} ${employee.lastName}"
            view.findViewById<TextView>(R.id.department).text = employee.department
            view.findViewById<TextView>(R.id.userTag).text = employee.userTag

            Glide.with(view.findViewById<ImageView>(R.id.avatar).context)
                .load(employee.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_avatar)
                .error(R.drawable.ic_baseline_avatar)
                .into(view.findViewById<ImageView>(R.id.avatar));

        }


    }
}