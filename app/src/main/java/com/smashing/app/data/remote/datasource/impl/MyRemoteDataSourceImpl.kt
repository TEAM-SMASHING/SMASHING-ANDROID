package com.smashing.app.data.remote.datasource.impl


import com.smashing.app.data.remote.datasource.api.MyRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.my.AddSportProfileRequest
import com.smashing.app.data.remote.dto.my.MyPageData
import com.smashing.app.data.remote.dto.my.MyProfileReviewListData
import com.smashing.app.data.remote.dto.my.MyProfileSwitchRequest
import com.smashing.app.data.remote.service.MyService
import jakarta.inject.Inject
class MyRemoteDataSourceImpl @Inject constructor(
    private val myService: MyService
) : MyRemoteDataSource {

    override suspend fun getMyProfile(): BaseResponse<MyPageData> {
        return myService.getMyProfile()
    }

    override suspend fun getMyGameReviews(
        cursor: String?,
        size: Int?
    ): BaseResponse<MyProfileReviewListData> {
        return myService.getMyGameReviews(cursor, size)
    }

    override suspend fun addSportProfile(request: AddSportProfileRequest): BaseResponse<String?> {
        return myService.addSportProfile(request)
    }

    override suspend fun putActiveMyProfile(request: MyProfileSwitchRequest): BaseResponse<Unit?> {
        return myService.putActiveMyProfile(request)
    }
}
