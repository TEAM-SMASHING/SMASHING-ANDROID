package com.smashing.app.data.remote.dto.event.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequesterDto(
    @SerialName("requesterProfileId")
    val requesterProfileId: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("tierCode")
    val tierCode: String,
    @SerialName("wins")
    val winCount: Int,
    @SerialName("losses")
    val loseCount: Int,
    @SerialName("reviewCount")
    val reviewCount: Long,
)
