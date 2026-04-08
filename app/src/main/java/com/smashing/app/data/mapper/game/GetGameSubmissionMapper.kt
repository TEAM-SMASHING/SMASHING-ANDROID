package com.smashing.app.data.mapper.game

import com.smashing.app.data.model.game.GameSubmissionDetail
import com.smashing.app.data.remote.dto.game.GetGameSubmissionResponse

fun GetGameSubmissionResponse.toModel(): GameSubmissionDetail {
    return GameSubmissionDetail(
        attemptNo = attemptNo,
        submitter = GameSubmissionDetail.SubmitterInfo(
            userId = submitter.userId,
            nickname = submitter.nickname,
            profileId = submitter.profileId,
        ),
        winner = GameSubmissionDetail.PlayerInfo(
            profileId = winner.profileId,
            nickname = winner.nickname,
        ),
        loser = GameSubmissionDetail.PlayerInfo(
            profileId = loser.profileId,
            nickname = loser.nickname,
        ),
    )
}
