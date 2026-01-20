package com.smashing.app.data.remote.dto.my

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageData(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("activeProfile")
    val activeProfile: ActiveProfileDto,
    @SerialName("allProfiles")
    val allProfiles: List<AllProfileDto>
)

@Serializable
data class ActiveProfileDto(
    @SerialName("profileId")
    val profileId: String,
    @SerialName("sportCode")
    val sportCode: String,
    @SerialName("tierCode")
    val tierCode: String,
    @SerialName("lp")
    val lp: Int,
    @SerialName("minLp")
    val minLp: Int,
    @SerialName("maxLp")
    val maxLp: Int,
    @SerialName("wins")
    val wins: Int,
    @SerialName("losses")
    val losses: Int,
    @SerialName("reviews")
    val reviews: Long
)

@Serializable
data class AllProfileDto(
    @SerialName("profileId")
    val profileId: String,
    @SerialName("sportCode")
    val sportCode: String,
    @SerialName("isActive")
    val isActive: Boolean
)
