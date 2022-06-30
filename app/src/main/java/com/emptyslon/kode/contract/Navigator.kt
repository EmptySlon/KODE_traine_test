package com.emptyslon.kode.contract

import androidx.fragment.app.Fragment
import com.emptyslon.kode.dataBase.Employee

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun restartFragment()

    fun showErrorFragment()

    fun showEmployeeDetails(employee: Employee)


}