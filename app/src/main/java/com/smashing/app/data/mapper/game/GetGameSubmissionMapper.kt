package com.smashing.app.data.mapper.game

import com.smashing.app.data.model.game.GameSubmissionDetail
import com.smashing.app.data.remote.dto.game.GetGameSubmissionResponse

fun GetGameSubmissionResponse.toModel(): GameSubmissionDetail {
    return GameSubmissionDetail(
        attemptNo = attemptNo,
        submitter = GameSubmissionDetail.SubmitterInfo(
            userId = submitter.userId,
            nickname = submitter.nickname,
        ),
        winner = GameSubmissionDetail.PlayerInfo(
            userId = winner.userId,
            nickname = winner.nickname,
            score = winner.score,
        ),
        loser = GameSubmissionDetail.PlayerInfo(
            userId = loser.userId,
            nickname = loser.nickname,
            score = loser.score,
        ),
    )
}
