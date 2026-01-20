package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.model.addsports.AddSportsInfo
import com.smashing.app.data.remote.datasource.api.AddSportsRemoteDataSource
import com.smashing.app.data.repository.api.AddSportsRepository
import jakarta.inject.Inject
import com.smashing.app.data.mapper.toRequest

class AddSportsRepositoryImpl @Inject constructor(
    private val addSportsRemoteDataSource: AddSportsRemoteDataSource
) : AddSportsRepository {
    override suspend fun addSportsProfile(info: AddSportsInfo): Result<Unit> = suspendRunCatching {

        val response = addSportsRemoteDataSource.addSportProfile(info.toRequest())
    }
}
