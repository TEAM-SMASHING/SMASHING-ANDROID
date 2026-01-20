package com.smashing.app.data.remote.dto.my

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMyTierProfileResponse(
    @SerialName("region")
    val region: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("activeProfile")
    val activeProfile: ActiveProfileResponse,
    @SerialName("allProfiles")
    val allProfiles: List<ProfileItemResponse>,
) {
    @Serializable
    data class ActiveProfileResponse(
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
    )

    @Serializable
    data class ProfileItemResponse(
        @SerialName("profileId")
        val profileId: String,
        @SerialName("sportCode")
        val sportCode: String,
        @SerialName("isActive")
        val isActive: Boolean,
    )
}