package com.smashing.app.core.common.type

enum class ReviewTagType(
    val tag: String,
) {
    GOOD_MANNER("경기 매너가 좋아요"),
    ON_TIME("시간 약속을 잘 지켜요"),
    FAIR_PLAY("승패를 깔끔하게 인정해요"),
    FAST_RESPONSE("응답이 빨라요");

    companion object {
        fun findReviewTagType(tag: String): ReviewTagType? {
            return entries.find { it.tag == tag }
        }
    }
}
