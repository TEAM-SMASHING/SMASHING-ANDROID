package com.smashing.app.presentation.withdraw.type

enum class WithdrawalDeletedType(
    val text: String,
) {
    PROFILE_AND_ACCOUNT("프로필 및 계정 정보"),
    MATCHING_AND_GAME_RECORDS("매칭 및 경기 기록"),
    REVIEWS_AND_RATINGS("작성한 후기 및 평가"),
    CHAT_HISTORY("채팅 내역"),
    NOTIFICATIONS_AND_BLOCK_REPORT_HISTORY("알림 및 차단 / 신고 내역"),
}