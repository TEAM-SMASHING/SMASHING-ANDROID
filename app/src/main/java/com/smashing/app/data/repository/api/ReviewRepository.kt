package com.smashing.app.data.repository.api

import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.model.search.SearchMainItemModel
import com.smashing.app.data.model.search.SuggestionItemModel
import com.smashing.app.data.remote.dto.search.GetRegionUsersSearchResponse
import com.smashing.app.data.type.OrderType

interface ReviewRepository {
    suspend fun getUserRecentList(
        userId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?,
    ): Result<CursorPage<GameReview>>

}
