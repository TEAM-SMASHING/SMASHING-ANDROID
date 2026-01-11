package com.smashing.app.core.util

import kotlin.math.absoluteValue

object ProfileImageProvider {
    // TODO 추후 프로필 이미지 변경 예정
    private val TEMP_PROFILE_IMAGE_URLS = listOf(
        "https://picsum.photos/100",
        "https://picsum.photos/100",
        "https://picsum.photos/100",
        "https://picsum.photos/100",
    )

    /**
     * userId를 기반으로 고정된 임시 URL을 반환합니다.
     * @param userId 사용자 식별자 (null일 경우 기본 이미지 반환)
     */
    fun getTempUrl(userId: String?): String {
        if (userId.isNullOrBlank()) return TEMP_PROFILE_IMAGE_URLS.first()

        val index = userId.hashCode().absoluteValue % TEMP_PROFILE_IMAGE_URLS.size
        return TEMP_PROFILE_IMAGE_URLS[index]
    }
}
