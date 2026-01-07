package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.model.common.CursorPage
import com.smashing.app.data.model.matching.ReceivedMatchingItem
import com.smashing.app.data.remote.dto.common.BaseResponse
import com.smashing.app.data.remote.dto.matching.GetMatchingListResponse

interface RemoteMatchingDataSource {
    suspend fun getReceivedMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Int?,
    ): BaseResponse<GetMatchingListResponse>
}
