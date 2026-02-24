package com.smashing.app.data.remote.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserInfoDetailResponse(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("isChallengeable")
    val isChallengeable: Boolean,
    @SerialName("isAcceptable")
    val isAcceptable: Boolean,
    @SerialName("receivedMatchingId")
    val receivedMatchingId: String? = null,
    @SerialName("selectedProfile")
    val selectedProfile: SelectedProfile,
    @SerialName("allProfiles")
    val allProfiles: List<Profile>
) {
    @Serializable
    data class SelectedProfile(
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
        val reviews: Long,
    )

    @Serializable
    data class Profile (
        @SerialName("profileId")
        val profileId: String,
        @SerialName("sportCode")
        val sportCode: String,
        @SerialName("isSelected")
        val isSelected: Boolean,
    )
}
