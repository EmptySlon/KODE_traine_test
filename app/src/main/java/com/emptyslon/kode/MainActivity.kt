package com.emptyslon.kode

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.emptyslon.kode.common.Common
import com.emptyslon.kode.common.Common.Companion.typeSorted
import com.emptyslon.kode.dataBase.EmployeesData
import com.emptyslon.kode.dataBase.EmployeesDataBase
import com.emptyslon.kode.databinding.ActivityMainBinding
import com.emptyslon.kode.retrofit.RetrofitClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var listUserFragment: ListUserFragment
    lateinit var valueTad: String


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        typeSorted = getString(R.string.sorted_alphabet)
        valueTad = Common.listDepartment.first()
        val inputSearch = binding.inputSearch

        AddTabInTabLayout()

        getData()

        inputSearch.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= inputSearch.right - inputSearch.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    showAlertOfSorted()
//                    Toast.makeText(this, "click work!!", Toast.LENGTH_SHORT).show()
                    return@OnTouchListener true
                }
            }
            false
        })

        binding.errWindow.findViewById<TextView>(R.id.err_tx_rebut).setOnClickListener {
            binding.errWindow.visibility = View.GONE
            getData()
        }

        binding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                valueTad = tab!!.text.toString()
                listUserFragment.refreshListData(EmployeesDataBase.listEmployees, valueTad)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }


    private fun AddTabInTabLayout() {
        for (department in Common.listDepartment) {
            val newTab = binding.tabCategory.newTab()
            newTab.text = department
            binding.tabCategory.addTab(newTab)
        }
    }

    private fun showAlertOfSorted() {
        val listTypeSorters =
            arrayOf(getString(R.string.sorted_alphabet), getString(R.string.sorted_birthday))
        val checkedItem = listTypeSorters.indexOf(typeSorted)

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.sorting))
            .setSingleChoiceItems(listTypeSorters, checkedItem) { dialogInterface, i ->
                typeSorted = listTypeSorters[i]
                EmployeesDataBase.sortedByType(typeSorted)
                listUserFragment.refreshListData(EmployeesDataBase.listEmployees, valueTad)
                dialogInterface.dismiss()
            }
            .show()
    }

    private fun changeDataInRecycleView(searchData: String) {

        val listFilteredEmployees = EmployeesDataBase
            .searchEmployees(searchData)
        listUserFragment.refreshListData(listFilteredEmployees, valueTad)

    }


    private fun getData() {
        val retrofitData = RetrofitClient.retrofit.getData()

        retrofitData.enqueue(object : Callback<EmployeesData?> {
            override fun onResponse(
                call: Call<EmployeesData?>,
                response: Response<EmployeesData?>
            ) {
                val listEmployees = response.body()?.employees!!
                listEmployees.map { it.avatarUrl = Common.listUrl.random() }
                EmployeesDataBase.listEmployees = listEmployees.toMutableList()
                EmployeesDataBase.sortedByType(typeSorted)

                findViewById<LinearLayout>(R.id.list_empty_user).visibility = View.GONE

                listUserFragment =
                    ListUserFragment(EmployeesDataBase.getListEmployeesFromDepartment(valueTad))
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.placeHolderListUsers,
                        listUserFragment
                    ).commit()
                binding.progressBar.visibility = ProgressBar.GONE

            }

            override fun onFailure(call: Call<EmployeesData?>, t: Throwable) {
                binding.errWindow.visibility = View.VISIBLE
            }
        })
    }
}

