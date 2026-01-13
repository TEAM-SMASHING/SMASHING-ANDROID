package com.smashing.app.data.model


//TODO api 변동되면 수정할 예정
data class ProfileReview(
    val id: Int,
    val reviewerName: String,
    val period: String,
    val content: String
)
