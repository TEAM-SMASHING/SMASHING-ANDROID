package com.smashing.app.data.mapper.auth

import com.smashing.app.data.model.auth.SignUpOpenchatValidModel
import com.smashing.app.data.remote.dto.auth.PostOpenchatValidResponse

fun PostOpenchatValidResponse.toSignUpOpenchatValidModel() = SignUpOpenchatValidModel(
    valid = this.valid,
)
