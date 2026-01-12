package com.smashing.app.data.mapper

import com.smashing.app.data.model.auth.KakaoLoginToken
import com.smashing.app.data.remote.dto.PostKakaoLoginResponse

fun PostKakaoLoginResponse.toKakaoLoginToken() = KakaoLoginToken(
    accessToken = this.accessToken ?: throw IllegalArgumentException("accessToken is null"),
    refreshToken = this.refreshToken ?: throw IllegalArgumentException("refreshToken is null"),
)
