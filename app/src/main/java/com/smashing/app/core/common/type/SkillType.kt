package com.smashing.app.core.common.type

enum class SkillType(
    val skillText: String,
    val skillCode: String,
) {
    THREE(
        skillText = "3개월 미만",
        skillCode = "IRON"
    ),
    THREETOSIX(
        skillText = "3개월 이상 ~ 6개월 미만",
        skillCode = "BRONZE_3"
    ),
    SIXTOYEAR(
        skillText = "6개월 이상 ~ 1년 미만",
        skillCode = "BRONZE_2"
    ),
    YEARTOYEARSIX(
        skillText = "1년 이상 ~ 1년 6개월 미만",
        skillCode = "BRONZE_1"
    ),
    YEARSIX(
        skillText = "1년 6개월 이상",
        skillCode = "SILVER_3"
    );
}
