package com.smashing.app.data.remote.datasource.api


import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.profile.my.MyPageData
import com.smashing.app.data.remote.dto.profile.my.MyProfileReviewListData

interface MyRemoteDataSource {
    suspend fun getMyProfile(): BaseResponse<MyPageData>

    suspend fun getMyGameReviews(cursor: String?, size: Int?): BaseResponse<MyProfileReviewListData>

}
