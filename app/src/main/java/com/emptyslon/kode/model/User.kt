package com.emptyslon.kode.model

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*


data class User(
    val id: String? = null,
    val avatarUrl: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val userTag: String? = null,
    val department: String? = null,
    val position: String? = null,
    val birthday: String? = null,
    val phone: String? = null,

    ) {

//    val age = Calendar.getInstance().time

}
//
//fun main() {
////    val data = Calendar.getInstance().time
////    data.time
////    val sdf = SimpleDateFormat("M/yyyy")
////    val dataBirthday = sdf.format()
////    val currentDate = sdf.format(data).let { println(it) }
//
//    val from = LocalDate.parse("27.07.1987", DateTimeFormatter.ofPattern("dd.MM.yyyy"))
//    val today = LocalDate.now()
//    var period = Period.between(from, today)
//    period.getYears().let { println(it) }
//
//}