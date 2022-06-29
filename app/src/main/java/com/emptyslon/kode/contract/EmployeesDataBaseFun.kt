package com.emptyslon.kode.contract

import android.icu.text.SimpleDateFormat
import com.emptyslon.kode.dataBase.Employee
import com.emptyslon.kode.dataBase.EmployeesDataBase

typealias listEmployees = MutableList<Employee>

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
//
//    fun searchEmployees(subString: String): List<Employee> {
//        return EmployeesDataBase.listEmployees.filter { employee ->
//            employee.isContainsSubstring(subString)
//        }
//
//    }
//
//    fun getEmployeeWithLastBirthdayInThisYear(): Employee {
//        val currentYear = java.util.Calendar.getInstance().time.year
//        val currentTime = java.util.Calendar.getInstance().time.time
//        return EmployeesDataBase.listEmployees.filter { employee ->
//            val birthday = SimpleDateFormat("yyyy-MM-dd")
//                .parse(employee.birthday)
//                .also { it.year = currentYear }.time
//            birthday - currentTime < 0
//        }.first()
//    }

    fun listEmployees.sortedByType(type: String): MutableList<Employee> {
        val currentYear = java.util.Calendar.getInstance().time.year
        val currentTime = java.util.Calendar.getInstance().time.time
        return if (type == "По алфавиту") this.sortedBy { it.firstName }
            .toMutableList()
        else this.sortedBy { employee ->
            val birthday = SimpleDateFormat("yyyy-MM-dd")
                .parse(employee.birthday)
                .also { it.year = currentYear }.time
            if (birthday - currentTime < 0) birthday - currentTime + 31536000000L
            else birthday - currentTime
        }.toMutableList()
    }

    fun listEmployees.filterFromDepartment(filterDepartment: String): MutableList<Employee> {
        return if (filterDepartment != "all")
            this.filter { it.department.lowercase() == filterDepartment }.toMutableList()
        else this
    }

}