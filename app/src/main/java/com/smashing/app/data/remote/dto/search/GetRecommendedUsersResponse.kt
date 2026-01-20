package com.smashing.app.data.remote.dto.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRecommendedUsersResponse(
    @SerialName("recommendedUsers")
    val recommendedUsers: List<GetRegionUsersSearchResponse>,
)