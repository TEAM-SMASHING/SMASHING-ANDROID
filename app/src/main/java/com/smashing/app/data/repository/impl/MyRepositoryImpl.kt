package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.my.toMyPageInfo
import com.smashing.app.data.mapper.my.toRequest
import com.smashing.app.data.mapper.my.toUserProfile
import com.smashing.app.data.model.my.UserProfile
import com.smashing.app.data.model.profile.MyPageInfo
import com.smashing.app.data.model.my.AddSportsInfo
import com.smashing.app.data.remote.datasource.api.MyRemoteDataSource
import com.smashing.app.data.remote.dto.my.MyProfileSwitchRequest
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.MyRepository
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myRemoteDataSource: MyRemoteDataSource
) : MyRepository {

    override suspend fun getMyPageInfo(): Result<MyPageInfo> = suspendRunCatching {
        myRemoteDataSource.getMyProfile()
            .requireData()
            .toMyPageInfo()
    }

    override suspend fun switchActiveMyProfile(
        profileId: String
    ): Result<Unit> = suspendRunCatching {
        myRemoteDataSource.putActiveMyProfile(MyProfileSwitchRequest(profileId = profileId))
    }

    override suspend fun addSportsProfile(info: AddSportsInfo): Result<Unit> = suspendRunCatching {
        myRemoteDataSource.addSportProfile(info.toRequest())

    }

    override suspend fun getMyTierProfile(): Result<UserProfile> = suspendRunCatching {
        myRemoteDataSource.getMyTierProfile()
            .requireData()
            .toUserProfile()
    }
}
