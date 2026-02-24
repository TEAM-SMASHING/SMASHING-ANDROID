package com.smashing.app.data.type

enum class TierType(
    val id: Long,
    val code: String,
    val tierName: String,
) {
    IRON(1, "IR", "Iron"),
    BRONZE_3(2, "BR3", "Bronze III"),
    BRONZE_2(3, "BR2", "Bronze II"),
    BRONZE_1(4, "BR1", "Bronze I"),
    SILVER_3(5, "SV3", "Silver III"),
    SILVER_2(6, "SV2", "Silver II"),
    SILVER_1(7, "SV1", "Silver I"),
    GOLD_3(8, "GO3", "Gold III"),
    GOLD_2(9, "GO2", "Gold II"),
    GOLD_1(10, "GO1", "Gold I"),
    PLATINUM_3(11, "PT3", "Platinum III"),
    PLATINUM_2(12, "PT2", "Platinum II"),
    PLATINUM_1(13, "PT1", "Platinum I"),
    DIAMOND_3(14, "DM3", "Diamond III"),
    DIAMOND_2(15, "DM2", "Diamond II"),
    DIAMOND_1(16, "DM1", "Diamond I"),
    CHALLENGER(17, "CH", "Challenger"),
    MAX(18, "MAX", "MAX");

    fun getNextTier(): TierType {
        val nextIndex = ordinal + 1
        return if (nextIndex < entries.size) entries[nextIndex] else this
    }

    companion object {
        private val CODE_MAP: Map<String, TierType> = entries.associateBy { it.code }
        fun findTierType(code: String): TierType = CODE_MAP[code] ?: IRON
    }
}
