package com.smashing.app.data.repository.api

import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.data.model.search.SearchMainItemModel
import com.smashing.app.data.model.search.SuggestionItemModel
import com.smashing.app.data.remote.dto.search.GetRegionUsersSearchResponse
import com.smashing.app.data.type.OrderType

interface SearchRepository {
    suspend fun getRegionUsersSearch(
        cursor: String?,
        size: Int?,
        gender: String?,
        tier: String?,
    ): Result<CursorPage<SearchMainItemModel>>

    suspend fun getNickNameUsersSearch(
        nickname: String,
    ): Result<List<SuggestionItemModel>>

    suspend fun getRecommendedUsers(): Result<List<SearchMainItemModel>>
}
