package com.emptyslon.kode

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListUserFragment (val listUser: List<String>)  : Fragment() {

//    private val listUser =
//        listOf<String>("All", "Designers", "Analysts", "Managers", "IOS", "Android")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_user, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleListUser)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = AdapterHeaderCategories(listUser)



        return view
    }

//    companion object {
//
//        @JvmStatic
//        fun newInstance() = ListUserFragment()
//
//    }
}