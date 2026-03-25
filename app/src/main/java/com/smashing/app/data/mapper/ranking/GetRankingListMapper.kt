package com.smashing.app.data.mapper.ranking

import com.smashing.app.data.model.rank.MyRank
import com.smashing.app.data.model.rank.Ranking
import com.smashing.app.data.model.rank.UserRank
import com.smashing.app.data.remote.dto.ranking.RankingListResponse
import com.smashing.app.data.type.TierType

fun RankingListResponse.toRankingData(): Ranking {
    return Ranking(
        topUsers = topUsers.map { it.toUserRankInfo() },
        myRank = user?.toMyRank(),
    )
}

private fun RankingListResponse.TopUserResponse.toUserRankInfo(): UserRank {
    return UserRank(
        rank = rank,
        userProfileId = userProfileId,
        nickname = nickname,
        tier = TierType.findTierType(tierCode),
        lp = lp,
    )
}

private fun RankingListResponse.UserResponse.toMyRank(): MyRank {
    return MyRank(
        nickname = nickname,
        tierType = TierType.findTierType(tierCode),
        lp = lp,
    )
}
