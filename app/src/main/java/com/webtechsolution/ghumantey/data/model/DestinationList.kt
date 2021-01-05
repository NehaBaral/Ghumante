package com.webtechsolution.ghumantey.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

class DestinationList : ArrayList<DestinationListItem>()

@Entity
data class DestinationListItem(
    @PrimaryKey
    val _id: String,
    val image: String,
    val location: String,
    val name: String
  //  val packages: List<Any>
)