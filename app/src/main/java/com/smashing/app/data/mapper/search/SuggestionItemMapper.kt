package com.smashing.app.data.mapper.search

import com.smashing.app.data.model.search.SuggestionItemModel
import com.smashing.app.data.remote.dto.search.GetNicknameUsersSearchResponse

fun GetNicknameUsersSearchResponse.toSuggestionItemModel(): List<SuggestionItemModel> =
    users.map {
        SuggestionItemModel(
            userId = it.userId,
            nickname = it.nickname,
        )
    }
