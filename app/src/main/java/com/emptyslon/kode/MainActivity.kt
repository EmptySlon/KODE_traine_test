package com.emptyslon.kode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.emptyslon.kode.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapterCategory: AdapterHeaderCategories
    val listCategories =
        listOf<String>("All", "Designers", "Analysts", "Managers", "IOS", "Android")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapterCategory = AdapterHeaderCategories(this, listCategories)
        binding.recycleCategory.adapter = adapterCategory
        binding.recycleCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


//        val adapter: ArrayAdapter<String>
//        adapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_list_item_1,
//            data
//        )
//        setListAdapter(adapter)


    }
}