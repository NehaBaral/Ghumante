package com.webtechsolution.ghumantey.data.domain

data class PostPackage(
    val description: String,
    val destination: String,
    val email: String,
    val excluded: String,
    val included: String,
    val iternaries: String,
    val name: String,
    val phone: Long,
    val price: Int
)