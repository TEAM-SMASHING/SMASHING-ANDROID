package com.smashing.app.data.mapper

import com.smashing.app.data.model.auth.SignUpModel
import com.smashing.app.data.remote.dto.auth.PostSignUpResponse

fun PostSignUpResponse.toSignUpModel() = SignUpModel(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
    userId = this.userId,
)
