package com.smashing.app.data.model.auth

data class TokenReissueModel(
    val accessToken: String,
    val refreshToken: String,
)
