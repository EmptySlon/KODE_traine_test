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
    fun isContainsSubstring (subString: String): Boolean {
        return when {
            department.contains(Regex(subString)) -> true
            firstName.contains(Regex(subString)) -> true
            lastName.contains(Regex(subString)) -> true
            phone.contains(Regex(subString)) -> true
            userTag.contains(Regex(subString)) -> true
            else -> false
        }

    }
}