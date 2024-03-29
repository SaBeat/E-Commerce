package com.example.e_commerce.data.entities.user

data class UserItem(
    val address: Address,
    val email: String,
    val name: Name,
    val password: String,
    val phone: String = "0000000",
    val username: String,
    val id: Int? = null
)

fun UserItem.toUser() = User(
    userName = username,
    userMail = email,
    userPassword = password,
    userId = id
)