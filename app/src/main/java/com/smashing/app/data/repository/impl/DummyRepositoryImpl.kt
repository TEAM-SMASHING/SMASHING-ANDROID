package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.toDummyUser
import com.smashing.app.data.model.DummyUser
import com.smashing.app.data.remote.datasource.api.DummyDataSource
import com.smashing.app.data.repository.api.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyDataSource: DummyDataSource,
) : DummyRepository {
    override suspend fun fetchDummyUserList(page: Int): Result<List<DummyUser>> =
        suspendRunCatching {
            val response = dummyDataSource.getDummyUserList(page)
            response.data?.map { it.toDummyUser() } ?: emptyList()
        }
}
