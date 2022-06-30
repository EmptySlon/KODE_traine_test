package com.emptyslon.kode


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emptyslon.kode.contract.Navigator
import com.emptyslon.kode.dataBase.Employee
import com.emptyslon.kode.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), Navigator {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, ListUserFragment())
            .commit()
    }

    override fun restartFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, ListUserFragment())
            .commit()
    }

    override fun showErrorFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, ErrorWindowFragment())
            .commit()
    }

    override fun showEmployeeDetails(employee: Employee) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.fragmentContainer, DetailsEmployeeFragment(employee))
            .commit()
    }

}

