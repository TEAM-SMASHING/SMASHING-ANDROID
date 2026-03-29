package com.smashing.app.data.model.game

data class GameSubmissionDetail(
    val attemptNo: Int,
    val submitter: SubmitterInfo,
    val winner: PlayerInfo,
    val loser: PlayerInfo,
) {
    data class SubmitterInfo(
        val userId: String,
        val nickname: String,
        val profileId: String,
    )

    data class PlayerInfo(
        val profileId: String,
        val nickname: String,
    )
}
