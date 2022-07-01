package com.emptyslon.kode.contract

import android.icu.text.SimpleDateFormat
import com.emptyslon.kode.dataBase.Employee

typealias listEmployees = List<Employee>

interface EmployeesDataBaseFun {

    fun listEmployees.getSetOfDepartment(): Set<String> = this.map{ it.department }.sorted().toSet()


    fun listEmployees.getEmployeeWithLastBirthdayInThisYear(): Employee? {
        val currentYear = java.util.Calendar.getInstance().time.year
        val currentTime = java.util.Calendar.getInstance().time.time
        return try {
            this.filter { employee ->
                val birthday = SimpleDateFormat("yyyy-MM-dd")
                    .parse(employee.birthday)
                    .also { it.year = currentYear }.time
                birthday - currentTime < 0
            }.first()
        } catch (e: NoSuchElementException) {
            null
        }
    }

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