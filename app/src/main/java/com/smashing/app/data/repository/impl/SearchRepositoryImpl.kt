package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.toSearchMainItemModelList
import com.smashing.app.data.mapper.toSuggestionItemModel
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.search.SearchMainItemModel
import com.smashing.app.data.model.search.SuggestionItemModel
import com.smashing.app.data.remote.datasource.api.SearchRemoteDataSource
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : SearchRepository {

    override suspend fun getRegionUsersSearch(
        cursor: String?,
        size: Int?,
        gender: String?,
        tier: String?
    ): Result<CursorPage<SearchMainItemModel>> = suspendRunCatching {
        searchRemoteDataSource.getRegionUsersSearch(
            cursor = cursor,
            size = size,
            gender = gender,
            tier = tier,
        ).requireData().toSearchMainItemModelList()
    }

    override suspend fun getNickNameUsersSearch(nickname: String): Result<List<SuggestionItemModel>> =
        suspendRunCatching {
            searchRemoteDataSource.getNickNameUsersSearch(
                nickname = nickname,
            ).requireData().toSuggestionItemModel()
        }
}
