package com.webtechsolution.ghumantey.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

class PackagesModel : ArrayList<PackagesModelItem>()

@Entity
data class PackagesModelItem(
    @PrimaryKey
    val _id: String,
    val agency: String,
    val destination: String,
    val detail: String,
    val duration: String,
    val excludes: List<Any>,
    val image: String,
    val includes: List<Any>,
    val itenaries: List<Any>,
    val price: Int,
    val title: String
)