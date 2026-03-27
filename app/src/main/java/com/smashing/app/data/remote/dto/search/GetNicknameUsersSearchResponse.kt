package com.smashing.app.data.remote.dto.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetNicknameUsersSearchResponse(
    @SerialName("users")
    val users: List<NickNameUser>,
) {
    @Serializable
    data class NickNameUser(
        @SerialName("userProfileId")
        val userProfileId: String,
        @SerialName("nickname")
        val nickname: String,
    )
}
