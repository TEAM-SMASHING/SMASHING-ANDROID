package com.smashing.app.data.model.review

import com.smashing.app.presentation.profile.myprofile.MyProfileContract
import kotlinx.collections.immutable.persistentListOf

data class GameReview(
    val gameReviewId: String = "",
    val opponentNickname: String = "",
    val createdAt: String = "",
    val content: String? = "",
    )
