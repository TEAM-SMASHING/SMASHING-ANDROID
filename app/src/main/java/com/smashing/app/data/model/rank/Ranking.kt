package com.smashing.app.data.model.rank

import com.smashing.app.data.type.TierType


data class Ranking(
    val topUsers: List<UserRank>,
    val myRank: MyRank?,
)

data class MyRank(
    val nickname: String,
    val tierType: TierType,
    val lp: Int,
)