package com.smashing.app.presentation.mypage.model

import com.smashing.app.data.type.TierType

data class MyPageProfileUiModel(
    val profileId: String = "",
    val nickname: String = "",
    val tierType: TierType = TierType.IRON,
)
