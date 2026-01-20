package com.smashing.app.data.remote.datasource.api


import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.my.AddSportProfileRequest
import com.smashing.app.data.remote.dto.my.MyPageData
import com.smashing.app.data.remote.dto.my.MyProfileReviewListData
import com.smashing.app.data.remote.dto.my.MyProfileSwitchRequest

interface MyRemoteDataSource {
    suspend fun getMyProfile(): BaseResponse<MyPageData>

    suspend fun getMyGameReviews(cursor: String?, size: Int?): BaseResponse<MyProfileReviewListData>

    suspend fun addSportProfile(request: AddSportProfileRequest): BaseResponse<Unit?>
    suspend fun putActiveMyProfile(request: MyProfileSwitchRequest): BaseResponse<Unit?>

    suspend fun getMyTierProfile(): BaseResponse<GetMyTierProfileResponse>
}