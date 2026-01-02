package com.smashing.app.data.repository.api

import com.smashing.app.data.model.DummyUser

interface DummyRepository {
    suspend fun fetchDummyUserList(page: Int): Result<List<DummyUser>>
}
