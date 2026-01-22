package com.smashing.app.data.model.auth

data class SignUpModel(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
    val userNickname: String,
)
