package com.smashing.app.data.type

enum class SkillType(
    val skillText: String,
    val skillCode: String,
) {
    THREE(
        skillText = "3개월 미만",
        skillCode = "LT_3_MONTHS"
    ),
    THREETOSIX(
        skillText = "3개월 이상 ~ 6개월 미만",
        skillCode = "LT_6_MONTHS"
    ),
    SIXTOYEAR(
        skillText = "6개월 이상 ~ 1년 미만",
        skillCode = "LT_1_YEAR"
    ),
    YEARTOYEARSIX(
        skillText = "1년 이상 ~ 1년 6개월 미만",
        skillCode = "LT_1_6_YEARS"
    ),
    YEARSIX(
        skillText = "1년 6개월 이상",
        skillCode = "GTE_2_YEARS"
    );
}
