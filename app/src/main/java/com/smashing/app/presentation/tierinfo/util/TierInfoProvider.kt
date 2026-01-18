package com.smashing.app.presentation.tierinfo.util

import android.content.Context
import com.smashing.app.R
import com.smashing.app.core.designsystem.style.TierInfoStyle
import com.smashing.app.data.model.rank.TierInfoDetail
import com.smashing.app.data.model.rank.TierProgressInfo
import com.smashing.app.data.model.rank.TierSkill
import com.smashing.app.data.type.SportType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

data class SkillResource(
    val nameRes: Int,
    val descriptionRes: Int,
)

data class TierDetailResource(
    val percentRes: Int,
    val levelRes: Int? = null,
    val skills: List<SkillResource>,
)

interface SportResourceProvider {
    fun getTierDetail(tier: TierInfoStyle): TierDetailResource
}

object BadmintonResourceProvider : SportResourceProvider {
    override fun getTierDetail(tier: TierInfoStyle): TierDetailResource = when (tier) {
        TierInfoStyle.IRON -> TierDetailResource(
            percentRes = R.string.tier_badminton_Iron_percent,
            skills = listOf(
                SkillResource(
                    R.string.skill_badminton_Iron_name,
                    R.string.skill_badminton_Iron_description
                ),
            )
        )

        TierInfoStyle.BRONZE -> TierDetailResource(
            percentRes = R.string.tier_badminton_bronze_percent,
            levelRes = R.string.tier_badminton_bronze_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_badminton_bronze_1_name,
                    R.string.skill_badminton_bronze_1_description
                ),
                SkillResource(
                    R.string.skill_badminton_bronze_2_name,
                    R.string.skill_badminton_bronze_2_description
                ),
                SkillResource(
                    R.string.skill_badminton_bronze_3_name,
                    R.string.skill_badminton_bronze_3_description
                ),
            )
        )

        TierInfoStyle.SILVER -> TierDetailResource(
            percentRes = R.string.tier_badminton_silver_percent,
            levelRes = R.string.tier_badminton_silver_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_badminton_silver_1_name,
                    R.string.skill_badminton_silver_1_description
                ),
                SkillResource(
                    R.string.skill_badminton_silver_2_name,
                    R.string.skill_badminton_silver_2_description
                ),
                SkillResource(
                    R.string.skill_badminton_silver_3_name,
                    R.string.skill_badminton_silver_3_description
                ),
            )
        )

        TierInfoStyle.GOLD -> TierDetailResource(
            percentRes = R.string.tier_badminton_gold_percent,
            levelRes = R.string.tier_badminton_gold_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_badminton_gold_1_name,
                    R.string.skill_badminton_gold_1_description
                ),
                SkillResource(
                    R.string.skill_badminton_gold_2_name,
                    R.string.skill_badminton_gold_2_description
                ),
                SkillResource(
                    R.string.skill_badminton_gold_3_name,
                    R.string.skill_badminton_gold_3_description
                ),
            )
        )

        TierInfoStyle.PLATINUM -> TierDetailResource(
            percentRes = R.string.tier_badminton_platinum_percent,
            levelRes = R.string.tier_badminton_platinum_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_badminton_platinum_1_name,
                    R.string.skill_badminton_platinum_1_description
                ),
                SkillResource(
                    R.string.skill_badminton_platinum_2_name,
                    R.string.skill_badminton_platinum_2_description
                ),
                SkillResource(
                    R.string.skill_badminton_platinum_3_name,
                    R.string.skill_badminton_platinum_3_description
                ),
            )
        )

        TierInfoStyle.DIAMOND -> TierDetailResource(
            percentRes = R.string.tier_badminton_diamond_percent,
            levelRes = R.string.tier_badminton_diamond_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_badminton_diamond_1_name,
                    R.string.skill_badminton_diamond_1_description
                ),
                SkillResource(
                    R.string.skill_badminton_diamond_2_name,
                    R.string.skill_badminton_diamond_2_description
                ),
                SkillResource(
                    R.string.skill_badminton_diamond_3_name,
                    R.string.skill_badminton_diamond_3_description
                ),
            )
        )

        TierInfoStyle.CHALLENGER -> TierDetailResource(
            percentRes = R.string.tier_badminton_challenger_percent,
            levelRes = R.string.tier_badminton_challenger_level,
            skills = listOf(
                SkillResource(R.string.tier_challenger_name, R.string.tier_challenger_description),
            ),
        )
    }
}

object PingPongResourceProvider : SportResourceProvider {
    override fun getTierDetail(tier: TierInfoStyle): TierDetailResource = when (tier) {
        TierInfoStyle.IRON -> TierDetailResource(
            percentRes = R.string.tier_pingpong_Iron_percent,
            skills = listOf(
                SkillResource(
                    R.string.skill_pingpong_Iron_name,
                    R.string.skill_pingpong_Iron_description
                ),
            )
        )

        TierInfoStyle.BRONZE -> TierDetailResource(
            percentRes = R.string.tier_pingpong_bronze_percent,
            levelRes = R.string.tier_pingpong_bronze_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_pingpong_bronze_1_name,
                    R.string.skill_pingpong_bronze_1_description
                ),
                SkillResource(
                    R.string.skill_pingpong_bronze_2_name,
                    R.string.skill_pingpong_bronze_2_description
                ),
                SkillResource(
                    R.string.skill_pingpong_bronze_3_name,
                    R.string.skill_pingpong_bronze_3_description
                ),
            )
        )

        TierInfoStyle.SILVER -> TierDetailResource(
            percentRes = R.string.tier_pingpong_silver_percent,
            levelRes = R.string.tier_pingpong_silver_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_pingpong_silver_1_name,
                    R.string.skill_pingpong_silver_1_description
                ),
                SkillResource(
                    R.string.skill_pingpong_silver_2_name,
                    R.string.skill_pingpong_silver_2_description
                ),
                SkillResource(
                    R.string.skill_pingpong_silver_3_name,
                    R.string.skill_pingpong_silver_3_description
                ),
            )
        )

        TierInfoStyle.GOLD -> TierDetailResource(
            percentRes = R.string.tier_pingpong_gold_percent,
            levelRes = R.string.tier_pingpong_gold_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_pingpong_gold_1_name,
                    R.string.skill_pingpong_gold_1_description
                ),
                SkillResource(
                    R.string.skill_pingpong_gold_2_name,
                    R.string.skill_pingpong_gold_2_description
                ),
                SkillResource(
                    R.string.skill_pingpong_gold_3_name,
                    R.string.skill_pingpong_gold_3_description
                ),
            )
        )

        TierInfoStyle.PLATINUM -> TierDetailResource(
            percentRes = R.string.tier_pingpong_platinum_percent,
            levelRes = R.string.tier_pingpong_platinum_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_pingpong_platinum_1_name,
                    R.string.skill_pingpong_platinum_1_description
                ),
                SkillResource(
                    R.string.skill_pingpong_platinum_2_name,
                    R.string.skill_pingpong_platinum_2_description
                ),
                SkillResource(
                    R.string.skill_pingpong_platinum_3_name,
                    R.string.skill_pingpong_platinum_3_description
                ),
            )
        )

        TierInfoStyle.DIAMOND -> TierDetailResource(
            percentRes = R.string.tier_pingpong_diamond_percent,
            levelRes = R.string.tier_pingpong_diamond_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_pingpong_diamond_1_name,
                    R.string.skill_pingpong_diamond_1_description
                ),
                SkillResource(
                    R.string.skill_pingpong_diamond_2_name,
                    R.string.skill_pingpong_diamond_2_description
                ),
                SkillResource(
                    R.string.skill_pingpong_diamond_3_name,
                    R.string.skill_pingpong_diamond_3_description
                ),
            )
        )

        TierInfoStyle.CHALLENGER -> TierDetailResource(
            percentRes = R.string.tier_pingpong_challenger_percent,
            levelRes = R.string.tier_pingpong_challenger_level,
            skills = listOf(
                SkillResource(R.string.tier_challenger_name, R.string.tier_challenger_description),
            ),
        )
    }
}

object TennisResourceProvider : SportResourceProvider {
    override fun getTierDetail(tier: TierInfoStyle): TierDetailResource = when (tier) {
        TierInfoStyle.IRON -> TierDetailResource(
            percentRes = R.string.tier_tennis_Iron_percent,
            skills = listOf(
                SkillResource(
                    R.string.skill_tennis_Iron_name,
                    R.string.skill_tennis_Iron_description
                ),
            )
        )

        TierInfoStyle.BRONZE -> TierDetailResource(
            percentRes = R.string.tier_tennis_bronze_percent,
            levelRes = R.string.tier_tennis_bronze_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_tennis_bronze_1_name,
                    R.string.skill_tennis_bronze_1_description
                ),
                SkillResource(
                    R.string.skill_tennis_bronze_2_name,
                    R.string.skill_tennis_bronze_2_description
                ),
                SkillResource(
                    R.string.skill_tennis_bronze_3_name,
                    R.string.skill_tennis_bronze_3_description
                ),
            )
        )

        TierInfoStyle.SILVER -> TierDetailResource(
            percentRes = R.string.tier_tennis_silver_percent,
            levelRes = R.string.tier_tennis_silver_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_tennis_silver_1_name,
                    R.string.skill_tennis_silver_1_description
                ),
                SkillResource(
                    R.string.skill_tennis_silver_2_name,
                    R.string.skill_tennis_silver_2_description
                ),
                SkillResource(
                    R.string.skill_tennis_silver_3_name,
                    R.string.skill_tennis_silver_3_description
                ),
            )
        )

        TierInfoStyle.GOLD -> TierDetailResource(
            percentRes = R.string.tier_tennis_gold_percent,
            levelRes = R.string.tier_tennis_gold_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_tennis_gold_1_name,
                    R.string.skill_tennis_gold_1_description
                ),
                SkillResource(
                    R.string.skill_tennis_gold_2_name,
                    R.string.skill_tennis_gold_2_description
                ),
                SkillResource(
                    R.string.skill_tennis_gold_3_name,
                    R.string.skill_tennis_gold_3_description
                ),
            )
        )

        TierInfoStyle.PLATINUM -> TierDetailResource(
            percentRes = R.string.tier_tennis_platinum_percent,
            levelRes = R.string.tier_tennis_platinum_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_tennis_platinum_1_name,
                    R.string.skill_tennis_platinum_1_description
                ),
                SkillResource(
                    R.string.skill_tennis_platinum_2_name,
                    R.string.skill_tennis_platinum_2_description
                ),
                SkillResource(
                    R.string.skill_tennis_platinum_3_name,
                    R.string.skill_tennis_platinum_3_description
                ),
            )
        )

        TierInfoStyle.DIAMOND -> TierDetailResource(
            percentRes = R.string.tier_tennis_diamond_percent,
            levelRes = R.string.tier_tennis_diamond_level,
            skills = listOf(
                SkillResource(
                    R.string.skill_tennis_diamond_1_name,
                    R.string.skill_tennis_diamond_1_description
                ),
                SkillResource(
                    R.string.skill_tennis_diamond_2_name,
                    R.string.skill_tennis_diamond_2_description
                ),
                SkillResource(
                    R.string.skill_tennis_diamond_3_name,
                    R.string.skill_tennis_diamond_3_description
                ),
            )
        )

        TierInfoStyle.CHALLENGER -> TierDetailResource(
            percentRes = R.string.tier_tennis_challenger_percent,
            levelRes = R.string.tier_tennis_challenger_level,
            skills = listOf(
                SkillResource(R.string.tier_challenger_name, R.string.tier_challenger_description),
            ),
        )
    }
}

@Singleton
class TierInfoProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun getTierInfoDetail(
        sportType: SportType,
        tierInfoStyle: TierInfoStyle,
    ): TierInfoDetail {
        val resourceProvider = getResourceProvider(sportType)
        val tierDetailResource = resourceProvider.getTierDetail(tierInfoStyle)

        val progressInfo = TierProgressInfo(
            percentText = context.getString(tierDetailResource.percentRes),
            levelText = context.getString(tierDetailResource.levelRes ?: R.string.blank_label),
        )

        val skills = tierDetailResource.skills.map { skillResource ->
            TierSkill(
                name = context.getString(skillResource.nameRes),
                description = context.getString(skillResource.descriptionRes),
            )
        }

        return TierInfoDetail(
            progressInfo = progressInfo,
            skills = skills,
            hasUpgrade = tierInfoStyle != TierInfoStyle.CHALLENGER,
        )
    }

    private fun getResourceProvider(sportType: SportType): SportResourceProvider =
        when (sportType) {
            SportType.BADMINTON -> BadmintonResourceProvider
            SportType.TENNIS -> TennisResourceProvider
            SportType.PING_PONG -> PingPongResourceProvider
        }
}