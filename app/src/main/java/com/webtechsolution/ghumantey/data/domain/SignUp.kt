package com.webtechsolution.ghumantey.data.domain

data class SignUp(
    val status: String,
    val success: Boolean,
    val agency:Boolean
)

data class SignUpBody(
    val username: String,
    val password: String,
    val agency:Boolean
)

data class Login(
    val status: String,
    val success: Boolean,
    val token: String,
    val agency:Boolean,
    val userId:String,
    val userName:String
)
