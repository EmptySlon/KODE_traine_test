package com.emptyslon.kode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emptyslon.kode.contract.EmployeesDataBaseFun
import com.emptyslon.kode.dataBase.Employee
import java.time.format.DateTimeFormatter
import java.util.*

class AdapterEmploees(
    var listEmployees: List<Employee>,

    ) :
    RecyclerView.Adapter<AdapterEmploees.CategoriesHolder>(), EmployeesDataBaseFun {

    private lateinit var mListener: onItemClickListener
    var isSortedByBirthday: Boolean = false

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setonItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return CategoriesHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        val employee = listEmployees[position]
        if (isSortedByBirthday && listEmployees.isNotEmpty()) {
            if (employee == listEmployees.getEmployeeWithLastBirthdayInThisYear()) {
                holder.bindWithBirthday(employee, true)
            } else holder.bindWithBirthday(employee, false)
        } else holder.bind(employee)
    }

    override fun getItemCount(): Int = listEmployees.size

    class CategoriesHolder(private val view: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(view) {

        init {
            view.findViewById<ImageView>(R.id.avatar).setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }


        fun bind(employee: Employee) {
            view.findViewById<FrameLayout>(R.id.included_year).visibility = View.GONE
            view.findViewById<TextView>(R.id.userName).text =
                "${employee.firstName} ${employee.lastName}"
            view.findViewById<TextView>(R.id.department).text = employee.department
            view.findViewById<TextView>(R.id.userTag).text = employee.userTag

            setFotoWithGlade(employee)
        }

        fun bindWithBirthday(employee: Employee, isEmployeeWithLastBirthdayInThisYear: Boolean) {
            view.findViewById<FrameLayout>(R.id.included_year).visibility = View.GONE

            val date = DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                .parse(employee.birthday)
            val desiredFormat = DateTimeFormatter
                .ofPattern("dd MMM", Locale("ru"))
                .format(date)
            with(view.findViewById<TextView>(R.id.birthday)) {
                visibility = View.VISIBLE
                text = desiredFormat

                if (isEmployeeWithLastBirthdayInThisYear) {
                    view.findViewById<FrameLayout>(R.id.included_year).visibility = View.VISIBLE
                    view.findViewById<TextView>(R.id.new_year_item_user).text =
                        (Calendar.getInstance().time.year + 1901).toString()
                }

            }
            view.findViewById<TextView>(R.id.userName).text =
                "${employee.firstName} ${employee.lastName}"
            view.findViewById<TextView>(R.id.department).text = employee.department
            view.findViewById<TextView>(R.id.userTag).text = employee.userTag

            setFotoWithGlade(employee);
        }

        private fun setFotoWithGlade(employee: Employee) {
            Glide.with(view.findViewById<ImageView>(R.id.avatar).context)
                .load(employee.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_avatar)
                .error(R.drawable.ic_baseline_avatar)
                .into(view.findViewById<ImageView>(R.id.avatar))
        }

    }
}