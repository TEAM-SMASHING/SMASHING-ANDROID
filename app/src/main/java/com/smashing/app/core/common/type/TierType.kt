package com.smashing.app.core.common.type

enum class TierType(
    val id: Long,
    val tierName: String,
) {
    IRON(1, "Iron"),
    BRONZE_3(2, "Bronze III"),
    BRONZE_2(3, "Bronze II"),
    BRONZE_1(4, "Bronze I"),
    SILVER_3(5, "Silver III"),
    SILVER_2(6, "Silver II"),
    SILVER_1(7, "Silver I"),
    GOLD_3(8, "Gold III"),
    GOLD_2(9, "Gold II"),
    GOLD_1(10, "Gold I"),
    PLATINUM_3(11, "Platinum III"),
    PLATINUM_2(12, "Platinum II"),
    PLATINUM_1(13, "Platinum I"),
    DIAMOND_3(14, "Diamond III"),
    DIAMOND_2(15, "Diamond II"),
    DIAMOND_1(16, "Diamond I"),
    CHALLENGER(17, "Challenger");

    companion object {
        fun findTierType(id: Long): TierType? {
            return TierType.entries.find { it.id == id }
        }
    }
}
