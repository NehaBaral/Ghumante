package com.webtechsolution.ghumantey.data.domain

class SearchPackage : ArrayList<SearchPackageItem>()

data class SearchPackageItem(
    val __v: Int,
    val _id: String,
    val agency: String,
    val bookings: List<Booking>,
    val comments: List<Comment>,
    val createdAt: String,
    val description: String,
    val destination: String,
    val email: String,
    val excluded: String,
    val included: String,
    val iternaries: String,
    val name: String,
    val phone: Long,
    val price: Int,
    val updatedAt: String
)
data class Booking(
    val _id: String,
    val author: String,
    val booking: Boolean,
    val createdAt: String,
    val updatedAt: String
)

data class Comment(
    val _id: String,
    val author: String,
    val comment: String,
    val createdAt: String,
    val rating: Int,
    val updatedAt: String
)