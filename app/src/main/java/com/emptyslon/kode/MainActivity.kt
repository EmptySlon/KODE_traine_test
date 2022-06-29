package com.emptyslon.kode

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.emptyslon.kode.common.Common
import com.emptyslon.kode.common.Common.Companion.typeSorted
import com.emptyslon.kode.contract.Navigator
import com.emptyslon.kode.dataBase.EmployeesData
import com.emptyslon.kode.dataBase.EmployeesDataBase
import com.emptyslon.kode.databinding.ActivityMainBinding
import com.emptyslon.kode.retrofit.RetrofitClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), Navigator  {
    lateinit var binding: ActivityMainBinding

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer)!!

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
//            updateUi()
        }
    }







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, ListUserFragment())
            .commit()

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)

    }

    override fun restartFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, ListUserFragment())
            .commit()
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }


//    private fun updateUi() {
//        val fragment = currentFragment
//
//        if (fragment is HasCustomTitle) {
//            binding.toolbar.title = getString(fragment.getTitleRes())
//        } else {
//            binding.toolbar.title = getString(R.string.fragment_navigation_example)
//        }
//
//        if (supportFragmentManager.backStackEntryCount > 0) {
//            supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            supportActionBar?.setDisplayShowHomeEnabled(true)
//        } else {
//            supportActionBar?.setDisplayHomeAsUpEnabled(false)
//            supportActionBar?.setDisplayShowHomeEnabled(false)
//        }
//
//        if (fragment is HasCustomAction) {
//            createCustomToolbarAction(fragment.getCustomAction())
//        } else {
//            binding.toolbar.menu.clear()
//        }
//    }


}

