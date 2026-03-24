package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import com.smashing.app.data.local.datasource.api.LocalUserDataSource
import com.smashing.app.data.mapper.my.toGameReviewResult
import com.smashing.app.data.mapper.my.toMyProfileInfo
import com.smashing.app.data.mapper.my.toMyProfileTierInfo
import com.smashing.app.data.mapper.my.toRequest
import com.smashing.app.data.model.addsports.AddSportsInfo
import com.smashing.app.data.model.profile.my.MyProfileInfo
import com.smashing.app.data.model.profile.home.MyProfileTierInfo
import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.remote.datasource.api.MyRemoteDataSource
import com.smashing.app.data.remote.dto.my.MyProfileSwitchRequest
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.MyRepository
import timber.log.Timber
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myRemoteDataSource: MyRemoteDataSource,
    private val tokenDataStore: LocalTokenDataSource,
    private val userDataStore: LocalUserDataSource,
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

    override suspend fun getMyRecentReviewStats(
    ): Result<GameReviewResult> = suspendRunCatching {
        myRemoteDataSource.getMyRecentReviewStats()
            .requireData()
            .toGameReviewResult()
    }

    override suspend fun postLogout(): Result<Unit> = suspendRunCatching {
        myRemoteDataSource.postLogout()
        clearLocalSession()
    }

    override suspend fun postWithdraw(): Result<Unit> = suspendRunCatching {
        myRemoteDataSource.postWithdraw()
        clearLocalSession()
    }

    private suspend fun clearLocalSession() {
        runCatching {
            tokenDataStore.clearTokens()
        }.onFailure {
            Timber.e(it, "Failed to clear tokens")
        }
        runCatching {
            userDataStore.clearUserInfo()
        }.onFailure {
            Timber.e(it, "Failed to clear user info")
        }
    }
}
