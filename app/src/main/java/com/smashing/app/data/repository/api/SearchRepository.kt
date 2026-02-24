package com.smashing.app.data.repository.api

import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.search.SearchMainItemModel
import com.smashing.app.data.model.search.SuggestionItemModel
import com.smashing.app.data.model.search.UserRegionItemModel

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

    suspend fun getUserRegion(): Result<UserRegionItemModel>
}
