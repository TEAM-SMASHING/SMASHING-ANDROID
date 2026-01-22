package com.smashing.app.core.util

import com.smashing.app.R.drawable.img_profile01_2
import com.smashing.app.R.drawable.img_profile02_2
import com.smashing.app.R.drawable.img_profile03_2
import com.smashing.app.R.drawable.img_default_profile

object ProfileImageProvider {
    private val TEMP_PROFILE_IMAGE_URLS = listOf(
        img_profile01_2,
        img_profile02_2,
        img_profile03_2,
    )

    /**
     * userId를 기반으로 고정된 임시 URL을 반환합니다.
     * @param nickname 사용자 식별자 (null일 경우 기본 이미지 반환)
     */
    fun getTempImg(nickname: String?): Int {
        if (nickname.isNullOrBlank()) return img_default_profile

        val index = nickname.fold(0) { acc, char -> acc + char.code } % TEMP_PROFILE_IMAGE_URLS.size
        return TEMP_PROFILE_IMAGE_URLS[index]
    }
}
