package com.smashing.app.core.designsystem.mapper

import androidx.annotation.DrawableRes
import com.smashing.app.R.drawable.ic_badminton
import com.smashing.app.R.drawable.ic_pingpong
import com.smashing.app.R.drawable.ic_tennis
import com.smashing.app.data.type.SportType

@DrawableRes
fun SportType.icon() = when (this) {
    SportType.PING_PONG -> ic_pingpong
    SportType.BADMINTON -> ic_badminton
    SportType.TENNIS -> ic_tennis
}
