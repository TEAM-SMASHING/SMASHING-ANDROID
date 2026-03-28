package com.smashing.app.data.type

import androidx.annotation.StringRes
import com.smashing.app.R.string.report_type_bad_manners
import com.smashing.app.R.string.report_type_etc
import com.smashing.app.R.string.report_type_post_match_dispute
import com.smashing.app.R.string.report_type_profanity_or_hate
import com.smashing.app.R.string.report_type_sexual_harassment

enum class ReportType(
    @StringRes val textResId: Int,
) {
    MANNER(report_type_bad_manners),
    ABUSE(report_type_profanity_or_hate),
    SEXUAL(report_type_sexual_harassment),
    DISPUTE(report_type_post_match_dispute),
    ETC(report_type_etc),
}
