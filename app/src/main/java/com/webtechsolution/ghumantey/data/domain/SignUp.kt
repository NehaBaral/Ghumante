package com.webtechsolution.ghumantey.data.domain

data class SignUp(
    val status: String,
    val success: Boolean
)

data class SignUpBody(
    val username: String,
    val password: String
)
