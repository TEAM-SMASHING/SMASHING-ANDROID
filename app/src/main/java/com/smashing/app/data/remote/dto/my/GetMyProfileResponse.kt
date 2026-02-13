package com.smashing.app.data.remote.dto.my

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMyProfileResponse(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("activeProfile")
    val activeProfile: MyProfileInfoResponse,
    @SerialName("allProfiles")
    val allProfiles: List<MyProfileItemResponse>
) {
    @Serializable
    data class MyProfileInfoResponse(
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
    data class MyProfileItemResponse(
        @SerialName("profileId")
        val profileId: String,
        @SerialName("sportCode")
        val sportCode: String,
        @SerialName("isActive")
        val isActive: Boolean
    )
}
