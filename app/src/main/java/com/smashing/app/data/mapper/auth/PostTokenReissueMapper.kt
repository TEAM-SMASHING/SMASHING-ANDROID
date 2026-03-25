package com.smashing.app.data.mapper.auth

import com.smashing.app.data.model.auth.TokenReissueModel
import com.smashing.app.data.remote.dto.auth.PostTokenReissueResponse

fun PostTokenReissueResponse.toTokenReissueModel() = TokenReissueModel(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
)
