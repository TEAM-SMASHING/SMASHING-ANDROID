package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.DummyDataSource
import com.smashing.app.data.remote.dto.GetUserListResponse
import com.smashing.app.data.remote.service.DummyService
import javax.inject.Inject

class DummyDataSourceImpl @Inject constructor(
    private val dummyService: DummyService,
) : DummyDataSource {
    override suspend fun getDummyUserList(page: Int): GetUserListResponse {
        return dummyService.getUserList(page = page)
    }
}
