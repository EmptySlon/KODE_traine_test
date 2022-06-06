package com.emptyslon.kode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.emptyslon.kode.dataBase.DataBase
import com.emptyslon.kode.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapterCategory: AdapterHeaderCategories
    val listCategories =
        listOf<String>("All", "Designers", "Analysts", "Managers", "IOS", "Android")
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val tab = inflater.inflate(R.layout.fragment_list_user, container, false)
//        val tab = TabLayout.Tab()
//        tab.text = "126"
//        binding.tabCategory.addTab(tab)





        binding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.placeHolderListUsers,
                        ListUserFragment(listCategories + listOf(counter++.toString()))
                    ).commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        val newTab = binding.tabCategory.newTab()
        newTab.text = "153"
//        binding.tabCategory.addTab(newTab)


//        adapterCategory = AdapterHeaderCategories(this, listCategories)
//        binding.recycleCategory.adapter = adapterCategory
//        binding.recycleCategory.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


    }
}