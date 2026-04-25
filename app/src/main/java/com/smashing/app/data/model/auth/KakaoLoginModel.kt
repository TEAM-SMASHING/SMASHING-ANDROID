package com.smashing.app.data.model.auth

data class KakaoLoginModel(
    val accessToken: String?,
    val refreshToken: String?,
    val socialId: String,
    val userId: String?,
    val userNickname: String?,
    val isCompletedSignUp: Boolean,
)
