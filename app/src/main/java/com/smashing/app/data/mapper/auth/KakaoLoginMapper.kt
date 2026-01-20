package com.smashing.app.data.mapper.auth

import com.smashing.app.data.model.auth.KakaoLoginModel
import com.smashing.app.data.remote.dto.auth.PostKakaoLoginResponse

fun PostKakaoLoginResponse.toKakaoLoginToken() = KakaoLoginModel(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
    kakaoId = this.kakaoId,
    userId = this.userId,
    userNickname = this.nickname,
    isCompletedSignUp = this.isCompletedSignUp,
)
