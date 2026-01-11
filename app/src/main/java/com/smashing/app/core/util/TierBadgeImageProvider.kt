package com.smashing.app.core.util

import com.smashing.app.core.common.type.TierType

object TierBadgeImageProvider {
    // TODO 추후 티어 배지 이미지 변경 예정
    private val TIER_BADGE_IMAGE_URLS = mapOf(
        "Iron" to "https://picsum.photos/100",
        "Bronze" to "https://picsum.photos/100",
        "Silver" to "https://picsum.photos/100",
        "Gold" to "https://picsum.photos/100",
        "Platinum" to "https://picsum.photos/100",
        "Diamond" to "https://picsum.photos/100",
        "Challenger" to "https://picsum.photos/100",
    )

    private val ROMAN_NUMERAL_REGEX = Regex("\\s+(I|II|III)$")

    fun getTierBadgeUrl(tierType: TierType): String {
        val tierNameWithoutRoman = tierType.tierName.replace(ROMAN_NUMERAL_REGEX, "").trim()

        return TIER_BADGE_IMAGE_URLS[tierNameWithoutRoman]
            ?: TIER_BADGE_IMAGE_URLS.values.first()
    }
}