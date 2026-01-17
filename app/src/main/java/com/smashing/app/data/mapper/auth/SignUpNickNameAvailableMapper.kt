package com.smashing.app.data.mapper.auth

import com.smashing.app.data.model.auth.SignUpNickNameAvailableModel
import com.smashing.app.data.remote.dto.auth.GetNickNameAvailableResponse

fun GetNickNameAvailableResponse.toSignUpNickNameAvailableModel() = SignUpNickNameAvailableModel(
    available = this.available,
)
