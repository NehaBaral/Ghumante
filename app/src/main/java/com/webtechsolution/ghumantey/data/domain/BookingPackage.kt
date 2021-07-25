package com.webtechsolution.ghumantey.data.domain

import java.util.*


data class BookingPackageItem(
    val _id: String,
    val author: Author,
    val booking: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val departureDate: String,
    val contactInfo: String,
    val peopleCount: String
)

data class BookPackageBody(
    val booking: Boolean,
    val departureDate:String,
    val contactInfo:String,
    val peopleCount:String
)