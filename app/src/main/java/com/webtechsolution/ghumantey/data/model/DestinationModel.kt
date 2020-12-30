package com.webtechsolution.ghumantey.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class DestinationModel(
    val name:String
)

@Entity
data class UserModel(
    @PrimaryKey
    val id:Int,
    val username:String
)


data class Register(
    val status:String,
    val message:String
)