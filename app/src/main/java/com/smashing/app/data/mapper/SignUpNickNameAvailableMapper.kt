package com.smashing.app.data.mapper

import com.smashing.app.data.model.auth.SignUpNickNameAvailableModel
import com.smashing.app.data.remote.dto.auth.GetNickNameAvailableResponse

fun GetNickNameAvailableResponse.toSignUpNickNameAvailableModel() = SignUpNickNameAvailableModel(
    available = this.available,
)
