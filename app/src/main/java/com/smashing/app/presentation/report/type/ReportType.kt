package com.smashing.app.presentation.report.type

enum class ReportType(
    val text: String,
) {
    BAD_MANNERS("비매너 사용자에요"),
    PROFANITY_OR_HATE("욕설, 비방, 혐오 표현을 사용해요"),
    SEXUAL_HARASSMENT("원치 않는 성적 대화 또는 만남을 요구해요"),
    POST_MATCH_DISPUTE("경기 이후 분쟁이 발생했어요\n(결과 조작, 패배 후 매칭 나감 등)"),
    ETC("기타"),
}