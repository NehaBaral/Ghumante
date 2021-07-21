package com.webtechsolution.ghumantey.data.domainModel

data class Author(
    val __v: Int,
    val _id: String,
    val admin: Boolean,
    val agency: Boolean,
    val firstname: String,
    val lastname: String,
    val myBookings: List<String>,
    val username: String
)