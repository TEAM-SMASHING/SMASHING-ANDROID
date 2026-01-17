package com.smashing.app.data.mapper

import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.search.SearchMainItemModel
import com.smashing.app.data.remote.dto.cursor.CursorDto
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
        userId = this.userId,
        nickname = this.nickname,
        gender = GenderType.findByName(this.gender),
        tierCode = TierType.findTierType(this.tierCode),
        wins = this.wins,
        losses = this.losses,
        reviews = this.reviews,
    )
}
