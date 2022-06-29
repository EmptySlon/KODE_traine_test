package com.emptyslon.kode.contract

import android.icu.text.SimpleDateFormat
import com.emptyslon.kode.dataBase.Employee
import com.emptyslon.kode.dataBase.EmployeesDataBase

typealias listEmployees = List<Employee>

interface EmployeesDataBaseFun {

//
//    var listEmployees: MutableList<Employee>
//
//    fun deleteEmployees() = EmployeesDataBase.listEmployees.clear()
//
//    fun getListEmployeesFromDepartment(department: String): List<Employee> {
//        return if (department.uppercase() == "ALL") EmployeesDataBase.listEmployees
//        else EmployeesDataBase.listEmployees.filter { it.department.uppercase() == department.uppercase() }
//    }

//    fun getEmployeeWithLastBirthdayInThisYear(): Employee {

    //    }
//        }.first()
//            birthday - currentTime < 0
//                .also { it.year = currentYear }.time
//                .parse(employee.birthday)
//            val birthday = SimpleDateFormat("yyyy-MM-dd")
//        return EmployeesDataBase.listEmployees.filter { employee ->
//        val currentTime = java.util.Calendar.getInstance().time.time
//        val currentYear = java.util.Calendar.getInstance().time.year

    fun listEmployees.searchEmployees(subString: String): List<Employee> {
        return this.filter { employee ->
            employee.isContainsSubstring(subString)
        }

    }

    fun listEmployees.sortedByType(type: String): List<Employee> {
        val currentYear = java.util.Calendar.getInstance().time.year
        val currentTime = java.util.Calendar.getInstance().time.time
        return if (type == "По алфавиту") this.sortedBy { it.firstName }
        else this.sortedBy { employee ->
            val birthday = SimpleDateFormat("yyyy-MM-dd")
                .parse(employee.birthday)
                .also { it.year = currentYear }.time
            if (birthday - currentTime < 0) birthday - currentTime + 31536000000L
            else birthday - currentTime
        }
    }

    fun listEmployees.filterFromDepartment(filterDepartment: String): List<Employee> {
        return if (filterDepartment != "all")
            this.filter { it.department.lowercase() == filterDepartment }
        else this
    }

}