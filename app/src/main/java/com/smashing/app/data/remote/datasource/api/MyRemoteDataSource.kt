package com.smashing.app.data.remote.datasource.api
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.my.AddSportProfileRequest
import com.smashing.app.data.remote.dto.my.GetMyProfileResponse
import com.smashing.app.data.remote.dto.my.GetMyRecentReviewStatsResponse
import com.smashing.app.data.remote.dto.my.GetMyTierProfileResponse
import com.smashing.app.data.remote.dto.my.MyProfileSwitchRequest

interface MyRemoteDataSource {
    suspend fun getMyTierProfile(): BaseResponse<GetMyTierProfileResponse>
    suspend fun getMyProfile(): BaseResponse<GetMyProfileResponse>

    suspend fun addSportProfile(request: AddSportProfileRequest): BaseResponse<Unit?>
    suspend fun putActiveMyProfile(request: MyProfileSwitchRequest): BaseResponse<Unit?>
    suspend fun getMyRecentReviewStats(): BaseResponse<GetMyRecentReviewStatsResponse>
    suspend fun postLogout(): BaseResponse<Unit>
}
