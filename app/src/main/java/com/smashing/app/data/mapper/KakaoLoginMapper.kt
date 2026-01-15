package com.smashing.app.data.mapper

import com.smashing.app.data.model.auth.KakaoLoginModel
import com.smashing.app.data.remote.dto.PostKakaoLoginResponse

fun PostKakaoLoginResponse.toKakaoLoginToken() = KakaoLoginModel(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
    kakaoId = this.kakaoId,
    userId = this.userId,
    isCompletedSignUp = this.isCompletedSignUp,
)
