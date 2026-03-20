package com.smashing.app.presentation.report.type

import androidx.annotation.StringRes
import com.smashing.app.R.string.report_type_bad_manners
import com.smashing.app.R.string.report_type_etc
import com.smashing.app.R.string.report_type_post_match_dispute
import com.smashing.app.R.string.report_type_profanity_or_hate
import com.smashing.app.R.string.report_type_sexual_harassment

enum class ReportType(
    @StringRes val textResId: Int,
) {
    BAD_MANNERS(report_type_bad_manners),
    PROFANITY_OR_HATE(report_type_profanity_or_hate),
    SEXUAL_HARASSMENT(report_type_sexual_harassment),
    POST_MATCH_DISPUTE(report_type_post_match_dispute),
    ETC(report_type_etc),
}