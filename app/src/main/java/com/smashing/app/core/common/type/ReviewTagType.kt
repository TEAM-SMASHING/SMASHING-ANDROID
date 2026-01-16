package com.smashing.app.core.common.type

enum class ReviewTagType(
    val tagLabel: String,
) {
    ON_TIME(
        tagLabel = "시간 약속을 잘 지켜요",
    ),
    GOOD_MANNER(
        tagLabel = "경기 매너가 좋아요",
    ),
    FAIR_PLAY(
        tagLabel = "승패를 깔끔하게 인정해요",
    ),
    FAST_RESPONSE(
        tagLabel = "응답이 빨라요",
    ),
}
