package com.webtechsolution.ghumantey.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class DestinationList(
    val `data`: List<Data>
)
@Entity
data class Data(
    @PrimaryKey
    val _id: String,
    val image: String,
    val location: String,
    val name: String
)