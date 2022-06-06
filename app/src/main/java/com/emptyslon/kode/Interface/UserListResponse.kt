package com.emptyslon.kode.Interface

data class UserListResponse (val items: List<User>) {
}

data class User(
 val id: String,
 val avatarUrl: String,
 val firstName: String,
 val lastName: String,
 val userTag: String,
 val department: String,
 val position: String,
 val birthday: String,
 val phone: String,

 ) {
}