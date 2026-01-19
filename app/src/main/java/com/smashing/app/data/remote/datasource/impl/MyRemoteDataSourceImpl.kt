package com.smashing.app.data.remote.datasource.impl


import com.smashing.app.data.remote.datasource.api.MyRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.profile.my.MyPageData
import com.smashing.app.data.remote.service.MyService
import javax.inject.Inject

class MyRemoteDataSourceImpl @Inject constructor(
    private val myService: MyService
) : MyRemoteDataSource {

    override suspend fun getMyProfile(): BaseResponse<MyPageData> {
        return myService.getMyProfile()
    }
}
