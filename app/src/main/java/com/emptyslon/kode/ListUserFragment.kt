package com.emptyslon.kode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emptyslon.kode.dataBase.UserListViewModel

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

//        val userListViewModel = ViewModelProviders.of(this).get(UserListViewModel::class.java)
//        userListViewModel.fetchUserList((activity?.application as? KodeApp)?.userApi)



        return view
    }

//    companion object {
//
//        @JvmStatic
//        fun newInstance() = ListUserFragment()
//
//    }
}