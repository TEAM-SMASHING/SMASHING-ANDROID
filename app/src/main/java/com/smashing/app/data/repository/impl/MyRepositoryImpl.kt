package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.toUserProfile
import com.smashing.app.data.model.my.UserProfile
import com.smashing.app.data.remote.datasource.api.MyRemoteDataSource
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.MyRepository
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myRemoteDataSource: MyRemoteDataSource,
) : MyRepository {

    override suspend fun getMyTierProfile(): Result<UserProfile> = suspendRunCatching {
        myRemoteDataSource.getMyTierProfile()
            .requireData()
            .toUserProfile()
    }
}