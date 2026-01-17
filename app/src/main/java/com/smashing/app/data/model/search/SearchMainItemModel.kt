package com.smashing.app.data.model.search

import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType
import javax.annotation.concurrent.Immutable

@Immutable
data class SearchMainItemModel(
    val userId: String,
    val nickname: String,
    val gender: GenderType,
    val tierCode: TierType,
    val wins: Int,
    val losses: Int,
    val reviews: Long,
)
