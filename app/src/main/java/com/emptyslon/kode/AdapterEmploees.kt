package com.emptyslon.kode

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emptyslon.kode.common.Common
import com.emptyslon.kode.dataBase.Employee
import com.emptyslon.kode.dataBase.EmployeesDataBase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AdapterEmploees(
    var listEmployees: List<Employee>,
    private val filterDepartment: String = "all"
) :
    RecyclerView.Adapter<AdapterEmploees.CategoriesHolder>() {

    private lateinit var mListener: onItemClickListener


    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setonItemClickListener (listener: onItemClickListener) {
        mListener = listener
    }

    init {
        if (filterDepartment != "all")
            listEmployees = listEmployees.filter { it.department.lowercase() == filterDepartment }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return CategoriesHolder(view, mListener, listEmployees)
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        val employee = listEmployees[position]
        holder.bind(employee)
    }

    override fun getItemCount(): Int = listEmployees.size


    class CategoriesHolder(private val view: View, listener: onItemClickListener, listEmployees: List<Employee>) :
        RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }



        fun bind(employee: Employee) {
            view.findViewById<FrameLayout>(R.id.included_year).visibility = View.GONE
            if (Common.typeSorted == view.context.getString(R.string.sorted_birthday)) {
                val date = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd")
                    .parse(employee.birthday)
                val desiredFormat = DateTimeFormatter
                    .ofPattern("dd MMM", Locale("ru"))
                    .format(date)
                with(view.findViewById<TextView>(R.id.birthday)) {
                    visibility = View.VISIBLE
                    text = desiredFormat
                }
                if (employee == EmployeesDataBase.getEmployeeWithLastBirthdayInThisYear()) {
                    view.findViewById<FrameLayout>(R.id.included_year).visibility = View.VISIBLE
                    view.findViewById<TextView>(R.id.new_year_item_user).text =
                        (Calendar.getInstance().time.year + 1901).toString()
                }

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