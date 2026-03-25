package com.smashing.app.data.mapper.search

import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.search.SearchMainItemModel
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.search.GetRecommendedUsersResponse
import com.smashing.app.data.remote.dto.search.GetRegionUsersSearchResponse
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType

fun CursorDto<GetRegionUsersSearchResponse>.toSearchMainItemModelList(): CursorPage<SearchMainItemModel> {
    return CursorPage(
        items = results.map { it.toSearchMainItemModel() },
        cursor = Cursor(
            snapshotAt = snapshotAt,
            nextCursor = nextCursor,
            hasNext = hasNext,
        ),
    )
}

private fun GetRegionUsersSearchResponse.toSearchMainItemModel(): SearchMainItemModel {
    return SearchMainItemModel(
        userProfileId = this.userProfileId,
        nickname = this.nickname,
        gender = GenderType.findByName(this.gender),
        tierType = TierType.findTierType(this.tierCode),
        wins = this.wins,
        losses = this.losses,
        reviews = this.reviews,
    )
}

fun GetRecommendedUsersResponse.toSearchMainItemModelList(): List<SearchMainItemModel> {
    return recommendedUsers.map { it.toSearchMainItemModel() }
}
