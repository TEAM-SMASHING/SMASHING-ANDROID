package com.smashing.app.data.type

enum class ReportType(
    val text: String,
) {
    MANNER("비매너 사용자에요"),
    ABUSE("욕설, 비방, 혐오 표현을 사용해요"),
    SEXUAL("원치 않는 성적 대화 또는 만남을 요구해요"),
    DISPUTE("경기 이후 분쟁이 발생했어요\n(결과 조작, 패배 후 매칭 나감 등)"),
    ETC("기타"),
}
