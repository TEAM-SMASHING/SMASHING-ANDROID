package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.notification.NotificationSummaryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationService {
    
    @GET("/api/v1/notifications/me")
    suspend fun getNotificationList(
        @Query("snapshotAt")
        snapshotAt: String?,
        @Query("cursor")
        cursor: String?,
        @Query("size")
        size: Long?,
    ): BaseResponse<CursorDto<NotificationSummaryResponse>>
}
