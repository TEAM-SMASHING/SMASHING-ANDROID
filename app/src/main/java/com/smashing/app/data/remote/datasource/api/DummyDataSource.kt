package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.GetUserListResponse

interface DummyDataSource {
    suspend fun getDummyUserList(page: Int): GetUserListResponse
}
