package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.RemoteMatchingDataSource
import com.smashing.app.data.remote.dto.common.BaseResponse
import com.smashing.app.data.remote.dto.matching.GetMatchingListResponse
import com.smashing.app.data.remote.mock.matching.ReceivedMatchingsMockData
import com.smashing.app.data.remote.service.MatchingService
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteMatchingDataSourceImpl @Inject constructor(
    private val matchingService: MatchingService,
) : RemoteMatchingDataSource {

    override suspend fun getReceivedMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Int?,
    ): BaseResponse<GetMatchingListResponse> {

        // TODO 추후 Mock 삭제 네트워크 지연
        delay(500)

        return ReceivedMatchingsMockData.mockReceivedMatchingResponse(hasNext = false)

        /*
        TODO: 실제 통신 코드
        return matchingService.getReceivedMatchings(
            snapshotAt = snapshotAt,
            cursor = cursor,
            size = size,
        )
         */
    }
}
