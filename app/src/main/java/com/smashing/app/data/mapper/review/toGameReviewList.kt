package com.smashing.app.data.mapper.review

import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.GetUserRecentListResponse

fun CursorDto<GetUserRecentListResponse>.toGameReviewList(): CursorPage<GameReview> {
    return CursorPage(
        items = results.map { it.toGameReview() },
        cursor = Cursor(
            snapshotAt = snapshotAt,
            nextCursor = nextCursor,
            hasNext = hasNext,
        ),
    )
}

private fun GetUserRecentListResponse.toGameReview(): GameReview {
    return GameReview(
        gameReviewId = this.gameReviewId,
        opponentNickname = this.opponentNickname,
        createdAt = this.createdAt,
        content = this.content,
    )
}
