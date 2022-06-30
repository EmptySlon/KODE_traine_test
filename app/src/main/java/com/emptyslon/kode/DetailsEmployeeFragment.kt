package com.emptyslon.kode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.emptyslon.kode.dataBase.Employee
import com.emptyslon.kode.databinding.FragmentDetailsEmployeeBinding


class DetailsEmployeeFragment (private val employee: Employee) : Fragment() {
    lateinit var binding: FragmentDetailsEmployeeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsEmployeeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        Glide.with(binding.avatarDetailsItem.context)
            .load(employee.avatarUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_avatar)
            .error(R.drawable.ic_baseline_avatar)
            .into(binding.avatarDetailsItem)
        binding.userNameDetailsItem.text =
            "${employee.firstName} ${employee.lastName}"
        binding.departmentDetailsItem.text = employee.department
        binding.userTagDetailsItem.text = employee.userTag
        binding.phoneNumber.text = employee.phone
        binding.iconBack.setOnClickListener {

            activity?.supportFragmentManager?.popBackStack()
        }


        return binding.root
    }


}