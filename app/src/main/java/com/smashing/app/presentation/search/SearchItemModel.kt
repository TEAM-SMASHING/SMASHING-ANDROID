package com.smashing.app.presentation.search

import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType
import javax.annotation.concurrent.Immutable

@Immutable
data class SearchItemModel(
    val userId: String,
    val nickname: String,
    val gender: GenderType,
    val tierId: TierType,
    val wins: Int,
    val losses: Int,
    val reviews: Int,
)
