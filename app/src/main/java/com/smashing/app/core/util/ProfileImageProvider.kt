package com.smashing.app.core.util

import com.smashing.app.R.drawable.img_default_profile
import com.smashing.app.R.drawable.img_profile_01
import com.smashing.app.R.drawable.img_profile_02
import com.smashing.app.R.drawable.img_profile_03
import com.smashing.app.R.drawable.img_profile_04
import com.smashing.app.R.drawable.img_profile_05
import com.smashing.app.R.drawable.img_profile_06
import com.smashing.app.R.drawable.img_profile_07

object ProfileImageProvider {
    private val TEMP_PROFILE_IMAGE_URLS = listOf(
        img_profile_01,
        img_profile_02,
        img_profile_03,
        img_profile_04,
        img_profile_05,
        img_profile_06,
        img_profile_07,
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
