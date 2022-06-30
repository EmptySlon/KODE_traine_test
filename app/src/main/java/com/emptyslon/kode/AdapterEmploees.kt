package com.emptyslon.kode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.emptyslon.kode.contract.EmployeesDataBaseFun
import com.emptyslon.kode.dataBase.Employee
import com.emptyslon.kode.databinding.ItemUserBinding
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
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoriesHolder(binding, mListener)
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

    class CategoriesHolder(val binding: ItemUserBinding, listener: onItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.avatar.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }


        }


        fun bind(employee: Employee) {


            binding.root.findViewById<FrameLayout>(R.id.included_year).visibility = View.GONE
            binding.userName.text = "${employee.firstName} ${employee.lastName}"
            binding.department.text = employee.department
            binding.userTag.text = employee.userTag

            setFotoWithGlade(employee)
        }

        fun bindWithBirthday(employee: Employee, isEmployeeWithLastBirthdayInThisYear: Boolean) {
            binding.root.findViewById<FrameLayout>(R.id.included_year).visibility = View.GONE

            val date = DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                .parse(employee.birthday)
            val desiredFormat = DateTimeFormatter
                .ofPattern("dd MMM", Locale("ru"))
                .format(date)
            with(binding.birthday) {
                visibility = View.VISIBLE
                text = desiredFormat

                if (isEmployeeWithLastBirthdayInThisYear) {
                    binding.root.findViewById<FrameLayout>(R.id.included_year).visibility =
                        View.VISIBLE
                    binding.root.findViewById<TextView>(R.id.new_year_item_user).text =
                        (Calendar.getInstance().time.year + 1901).toString()
                }

            }
            binding.userName.text =
                "${employee.firstName} ${employee.lastName}"
            binding.department.text = employee.department
            binding.userTag.text = employee.userTag

            setFotoWithGlade(employee);
        }

        private fun setFotoWithGlade(employee: Employee) {
            Glide.with(binding.avatar.context)
                .load(employee.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_avatar)
                .error(R.drawable.ic_baseline_avatar)
                .into(binding.avatar)

        }

    }
}