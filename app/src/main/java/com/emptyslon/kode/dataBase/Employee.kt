package com.emptyslon.kode.dataBase

data class Employee(
    var avatarUrl: String,
    val birthday: String,
    val department: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val phone: String,
    val position: String,
    val userTag: String
) {
    fun isContainsSubstring(subString: String): Boolean {
        return when {
            department.contains(subString, true) -> true
            firstName.contains(subString, true) -> true
            lastName.contains(subString, true) -> true
            phone.contains(subString, true) -> true
            userTag.contains(subString, true) -> true
            else -> false
        }

    }
}