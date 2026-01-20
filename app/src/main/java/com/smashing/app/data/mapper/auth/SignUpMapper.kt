package com.smashing.app.data.mapper.auth

import com.smashing.app.data.model.auth.SignUpModel
import com.smashing.app.data.remote.dto.auth.PostSignUpResponse

fun PostSignUpResponse.toSignUpModel() = SignUpModel(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
    userId = this.userId,
    userNickname = this.userNickname,
)
