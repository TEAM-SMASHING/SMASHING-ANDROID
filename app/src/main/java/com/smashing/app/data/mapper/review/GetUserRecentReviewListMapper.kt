package com.smashing.app.data.mapper.review

import com.smashing.app.core.util.ConvertTimeProvider.convertLocalDateTimeToTime
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.GetMyRecentReviewListResponse
import com.smashing.app.data.remote.dto.review.GetUserRecentReviewListResponse

fun CursorDto<GetUserRecentReviewListResponse>.toGameReviewList(): CursorPage<GameReview> {
    return CursorPage(
        items = results.map { it.toGameReview() },
        cursor = Cursor(
            snapshotAt = snapshotAt,
            nextCursor = nextCursor,
            hasNext = hasNext,
        ),
    )
}

private fun GetUserRecentReviewListResponse.toGameReview(): GameReview {
    return GameReview(
        gameReviewId = this.gameReviewId,
        opponentNickname = this.opponentNickname,
        createdAt = convertLocalDateTimeToTime(this.createdAt),
        content = this.content,
    )
}

fun GetMyRecentReviewListResponse.toGameReviewPage(): CursorPage<GameReview> {
    return CursorPage(
        items = this.results.map { it.toGameReview() },
        cursor = Cursor(
            snapshotAt = this.snapshotAt,
            nextCursor = this.nextCursor,
            hasNext = this.hasNext
        )
    )
}
