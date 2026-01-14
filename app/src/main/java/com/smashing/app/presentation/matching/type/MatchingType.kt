package com.smashing.app.presentation.matching.type

import androidx.annotation.StringRes
import com.smashing.app.R.string.matching_confirm
import com.smashing.app.R.string.matching_receive
import com.smashing.app.R.string.matching_send

enum class MatchingType(
    @StringRes val labelRes: Int,
) {
    RECEIVE(matching_receive),
    SEND(matching_send),
    ACCEPTED(matching_confirm),
}
