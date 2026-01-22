package com.smashing.app.data.model.profile

data class UserProfileInfo(
    val profileInfo: ProfileInfo,
    val isChallengeable: Boolean,
    val isAcceptable: Boolean,
    val receivedMatchingId:	String?,
    val sportProfile: List<SportProfile>,
)
