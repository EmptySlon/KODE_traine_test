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


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        typeSorted = getString(R.string.sorted_alphabet)

        for (department in Common.listDepartment) {
            val newTab = binding.tabCategory.newTab()
            newTab.text = department
            binding.tabCategory.addTab(newTab)
        }

        resources


        val inputSearch = binding.inputSearch
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

        getData()

        binding.errWindow.findViewById<TextView>(R.id.err_tx_rebut).setOnClickListener {
            binding.errWindow.visibility = View.GONE
            getData()
        }




        binding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.placeHolderListUsers,
                        ListUserFragment(EmployeesDataBase.getListEmployeesFromDepartment(tab!!.text.toString()))
                    ).commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

    private fun showAlertOfSorted() {

        val listTypeSorters =
            arrayOf(getString(R.string.sorted_alphabet), getString(R.string.sorted_birthday))
        val checkedItem = listTypeSorters.indexOf(typeSorted)

        MaterialAlertDialogBuilder(this)
            .setTitle("Сортировка")
            .setSingleChoiceItems(listTypeSorters, checkedItem) { dialogInterface, i ->
                typeSorted = listTypeSorters[i]
                Log.e("ErTag", typeSorted)
                changeDataInRecycleView()
                dialogInterface.dismiss()
            }
            .show()
    }

    private fun changeDataInRecycleView(searchData: String = "ALL") {
        val listFilteredEmployees = EmployeesDataBase.searchEmployees(searchData)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.placeHolderListUsers,
                ListUserFragment(listFilteredEmployees)
            ).commit()

    }


    private fun getData() {
        val retrofitData = RetrofitClient.retrofit.getData()
        retrofitData.enqueue(object : Callback<EmployeesData?> {
            override fun onResponse(
                call: Call<EmployeesData?>,
                response: Response<EmployeesData?>
            ) {
                var listEmployees = response.body()?.employees!!
                listEmployees = if (typeSorted == getString(R.string.sorted_alphabet)) {
                    listEmployees.sortedBy { it.firstName }
                } else listEmployees.sortedBy { it.birthday }
                listEmployees.map { it.avatarUrl = Common.listUrl.random() }
                listEmployees.map { EmployeesDataBase.listEmployees.add(it) }
                findViewById<LinearLayout>(R.id.list_empty_user).visibility = View.GONE
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.placeHolderListUsers,
                        ListUserFragment(listEmployees)
                    ).commit()
                binding.progressBar.visibility = ProgressBar.GONE

            }

            override fun onFailure(call: Call<EmployeesData?>, t: Throwable) {
                binding.errWindow.visibility = View.VISIBLE
            }
        })
    }
}

