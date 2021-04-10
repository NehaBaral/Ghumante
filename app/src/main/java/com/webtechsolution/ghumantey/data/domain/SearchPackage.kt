package com.webtechsolution.ghumantey.data.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

class SearchPackage : ArrayList<SearchPackageItem>()

@Entity
data class SearchPackageItem(
    val __v: Int,
    @PrimaryKey
    val _id: String,
    val agency: String,
    val bookings: List<BookingPackageItem>,
    val comments: List<CommentItem>,
    val createdAt: String,
    val description: String,
    val destination: String,
    val name: String,
    val price: Int,
    val updatedAt: String
)