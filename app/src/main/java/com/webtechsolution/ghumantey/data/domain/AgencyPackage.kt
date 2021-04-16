package com.webtechsolution.ghumantey.data.domain

class AgencyPackage : ArrayList<AgencyPackageItem>()

data class AgencyPackageItem(
    val __v: Int,
    val _id: String,
    val agency: Agency,
    val bookings: List<BookingPackageItem>,
    val comments: List<CommentItem>,
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

data class Agency(
    val __v: Int,
    val _id: String,
    val admin: Boolean,
    val agency: Boolean,
    val firstname: String,
    val lastname: String,
    val myBookings: List<Any>,
    val username: String
)