package com.smashing.app.core.common.type

enum class TierType(
    val id: Long,
) {
    IRON(1),
    BRONZE_3(2),
    BRONZE_2(3),
    BRONZE_1(4),
    SILVER_3(5),
    SILVER_2(6),
    SILVER_1(7),
    GOLD_3(8),
    GOLD_2(9),
    GOLD_1(10),
    PLATINUM_3(11),
    PLATINUM_2(12),
    PLATINUM_1(13),
    DIAMOND_3(14),
    DIAMOND_2(15),
    DIAMOND_1(16),
    CHALLENGER(17);

    companion object {
        fun findTierType(id: Long): TierType? {
            return TierType.entries.find { it.id == id }
        }
    }
}
