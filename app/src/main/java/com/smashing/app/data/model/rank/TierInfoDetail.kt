package com.smashing.app.data.model.rank

data class TierSkill(
    val name: String,
    val description: String,
)

data class TierProgressInfo(
    val percentText: String?,
    val levelText: String?,
)

data class TierInfoDetail(
    val progressInfo: TierProgressInfo,
    val skills: List<TierSkill>,
    val hasUpgrade: Boolean = true,
)