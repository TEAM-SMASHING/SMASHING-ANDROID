package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.my.toMyProfileInfo
import com.smashing.app.data.mapper.my.toMyProfileTierInfo
import com.smashing.app.data.mapper.my.toRequest
import com.smashing.app.data.model.addsports.AddSportsInfo
import com.smashing.app.data.model.profile.my.MyProfileInfo
import com.smashing.app.data.model.profile.home.MyProfileTierInfo
import com.smashing.app.data.remote.datasource.api.MyRemoteDataSource
import com.smashing.app.data.remote.dto.my.MyProfileSwitchRequest
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.MyRepository
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myRemoteDataSource: MyRemoteDataSource
) : MyRepository {

    override suspend fun getMyTierProfile(): Result<MyProfileTierInfo> = suspendRunCatching {
        myRemoteDataSource.getMyTierProfile()
            .requireData()
            .toMyProfileTierInfo()
    }

    override suspend fun getMyProfileInfo(): Result<MyProfileInfo> = suspendRunCatching {
        myRemoteDataSource.getMyProfile()
            .requireData()
            .toMyProfileInfo()
    }


    override suspend fun switchActiveMyProfile(
        profileId: String
    ): Result<Unit> = suspendRunCatching {
        myRemoteDataSource.putActiveMyProfile(MyProfileSwitchRequest(profileId = profileId))
    }

    override suspend fun addSportsProfile(
        info: AddSportsInfo
    ): Result<Unit> = suspendRunCatching {
        myRemoteDataSource.addSportProfile(info.toRequest())
    }
}
