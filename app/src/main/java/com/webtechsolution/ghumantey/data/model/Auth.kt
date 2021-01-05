package com.webtechsolution.ghumantey.data.model

data class Auth(
    val status: String,
    val success: Boolean,
    val token: String,
    val username: String
)

data class Register(
    val email: String,
    val status: String,
    val success: Boolean,
    val username: String
)