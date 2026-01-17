package com.smashing.app.core.designsystem.mapper

import androidx.annotation.DrawableRes
import com.smashing.app.R.drawable.ic_man_20
import com.smashing.app.R.drawable.ic_man_32
import com.smashing.app.R.drawable.ic_woman_20
import com.smashing.app.R.drawable.ic_woman_32
import com.smashing.app.data.type.GenderType

@DrawableRes
fun GenderType.icon20() = when (this) {
    GenderType.MALE -> ic_man_20
    GenderType.FEMALE -> ic_woman_20
}

@DrawableRes
fun GenderType.icon32() = when (this) {
    GenderType.MALE -> ic_man_32
    GenderType.FEMALE -> ic_woman_32
}
