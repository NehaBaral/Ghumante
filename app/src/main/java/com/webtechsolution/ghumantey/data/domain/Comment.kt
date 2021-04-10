package com.webtechsolution.ghumantey.data.domain


data class CommentItem(
    val _id: String,
    val author: String,
    val comment: String,
    val createdAt: String,
    val rating: Int,
    val updatedAt: String
)