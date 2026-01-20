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
        val request = info.toRequest()

        val response = addSportsRemoteDataSource.addSportProfile(request)

        if (response.statusCode == 200) {
        //성공
        } else {
            throw IllegalStateException(response.status)
        }
    }
}
