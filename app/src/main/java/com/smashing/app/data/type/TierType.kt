package com.smashing.app.data.type

enum class TierType(
    val id: Long,
    val tierName: String,
) {
    IRON_1(1, "Iron"),
    BRONZE_3_1(2, "Bronze III"),
    BRONZE_2_1(3, "Bronze II"),
    BRONZE_1_1(4, "Bronze I"),
    SILVER_3_1(5, "Silver III"),
    SILVER_2_1(6, "Silver II"),
    SILVER_1_1(7, "Silver I"),
    GOLD_3_1(8, "Gold III"),
    GOLD_2_1(9, "Gold II"),
    GOLD_1_1(10, "Gold I"),
    PLATINUM_3_1(11, "Platinum III"),
    PLATINUM_2_1(12, "Platinum II"),
    PLATINUM_1_1(13, "Platinum I"),
    DIAMOND_3_1(14, "Diamond III"),
    DIAMOND_2_1(15, "Diamond II"),
    DIAMOND_1_1(16, "Diamond I"),
    CHALLENGER_1(17, "Challenger"),
    IRON_2(18, "Iron"),
    BRONZE_3_2(19, "Bronze III"),
    BRONZE_2_2(20, "Bronze II"),
    BRONZE_1_2(21, "Bronze I"),
    SILVER_3_2(22, "Silver III"),
    SILVER_2_2(23, "Silver II"),
    SILVER_1_2(24, "Silver I"),
    GOLD_3_2(25, "Gold III"),
    GOLD_2_2(26, "Gold II"),
    GOLD_1_2(27, "Gold I"),
    PLATINUM_3_2(28, "Platinum III"),
    PLATINUM_2_2(29, "Platinum II"),
    PLATINUM_1_2(30, "Platinum I"),
    DIAMOND_3_2(31, "Diamond III"),
    DIAMOND_2_2(32, "Diamond II"),
    DIAMOND_1_2(33, "Diamond I"),
    CHALLENGER_2(34, "Challenger"),
    IRON_3(35, "Iron"),
    BRONZE_3_3(36, "Bronze III"),
    BRONZE_2_3(37, "Bronze II"),
    BRONZE_1_3(38, "Bronze I"),
    SILVER_3_3(39, "Silver III"),
    SILVER_2_3(40, "Silver II"),
    SILVER_1_3(41, "Silver I"),
    GOLD_3_3(42, "Gold III"),
    GOLD_2_3(43, "Gold II"),
    GOLD_1_3(44, "Gold I"),
    PLATINUM_3_3(45, "Platinum III"),
    PLATINUM_2_3(46, "Platinum II"),
    PLATINUM_1_3(47, "Platinum I"),
    DIAMOND_3_3(48, "Diamond III"),
    DIAMOND_2_3(49, "Diamond II"),
    DIAMOND_1_3(50, "Diamond I"),
    CHALLENGER_3(51, "Challenger");

    fun getNextTier(): TierType {
        return ID_MAP[this.id + 1] ?: this
    }

    companion object {
        private val ID_MAP: Map<Long, TierType> = entries.associateBy { it.id }
        fun findTierType(id: Long): TierType = ID_MAP[id] ?: IRON_1
    }
}
