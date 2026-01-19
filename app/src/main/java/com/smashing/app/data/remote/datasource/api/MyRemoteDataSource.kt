package com.smashing.app.data.remote.datasource.api


import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.profile.my.MyPageData

interface MyRemoteDataSource {
    suspend fun getMyProfile(): BaseResponse<MyPageData>
}
