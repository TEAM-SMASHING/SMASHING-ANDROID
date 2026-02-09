package com.smashing.app.data.remote.datasource.impl


import com.smashing.app.data.remote.datasource.api.MyRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.my.AddSportProfileRequest
import com.smashing.app.data.remote.dto.my.GetMyTierProfileResponse
import com.smashing.app.data.remote.dto.my.GetMyProfileResponse
import com.smashing.app.data.remote.dto.my.MyProfileSwitchRequest
import com.smashing.app.data.remote.service.MyService
import jakarta.inject.Inject

class MyRemoteDataSourceImpl @Inject constructor(
    private val myService: MyService
) : MyRemoteDataSource {

    override suspend fun getMyTierProfile(): BaseResponse<GetMyTierProfileResponse> =
        myService.getMyTierProfile()

    override suspend fun getMyProfile(): BaseResponse<GetMyProfileResponse> {
        return myService.getMyProfile()
    }

    override suspend fun addSportProfile(request: AddSportProfileRequest): BaseResponse<Unit?> {
        return myService.addSportProfile(request)
    }

    override suspend fun putActiveMyProfile(request: MyProfileSwitchRequest): BaseResponse<Unit?> {
        return myService.putActiveMyProfile(request)
    }
}
