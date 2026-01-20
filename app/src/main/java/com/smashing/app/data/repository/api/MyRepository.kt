package com.smashing.app.data.repository.api

import com.smashing.app.data.model.my.UserProfile

interface MyRepository {
    suspend fun getMyTierProfile(): Result<UserProfile>
}