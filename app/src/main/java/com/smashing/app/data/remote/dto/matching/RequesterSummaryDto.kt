package com.smashing.app.data.remote.dto.matching

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequesterSummaryDto(
    @SerialName("userId")
    val userId: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("reviewCount")
    val reviewCount: Int,
    @SerialName("tierId")
    val tierId: Long,
    @SerialName("gender")
    val gender: String,
    @SerialName("wins")
    val winCount: Int,
    @SerialName("losses")
    val loseCount: Int,
)
