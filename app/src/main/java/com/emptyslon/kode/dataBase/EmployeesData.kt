package com.emptyslon.kode.dataBase

import com.google.gson.annotations.SerializedName




//data class EmployeesData(
//    val employees: List<Employees>
//)


class EmployeesData {
    @SerializedName("items")
    val employees: List<Employees>? = null
}