package com.smashing.app.data.mapper

import com.smashing.app.data.model.auth.AuthModel
import com.smashing.app.data.remote.dto.PostSignUpResponse

fun PostSignUpResponse.toSignUpModel() = AuthModel(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
    authId = this.authId,
)
