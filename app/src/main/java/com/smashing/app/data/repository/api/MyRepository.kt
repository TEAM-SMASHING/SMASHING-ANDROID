package com.smashing.app.data.repository.api

import com.smashing.app.data.model.profile.MyPageInfo


interface MyRepository {
    suspend fun getMyPageInfo(): Result<MyPageInfo>
}

