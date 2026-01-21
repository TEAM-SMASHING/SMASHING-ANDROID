package com.smashing.app.data.mapper.search

import com.smashing.app.data.model.search.UserRegionItemModel
import com.smashing.app.data.remote.dto.search.GetUserRegionResponse

fun GetUserRegionResponse.toUserRegionItem(): UserRegionItemModel =
    UserRegionItemModel(
        region = this.region
    )
