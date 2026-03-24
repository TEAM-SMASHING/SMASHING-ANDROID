package com.smashing.app.presentation.withdraw.type

import androidx.annotation.StringRes
import com.smashing.app.R.string.withdraw_deleted_chat_history
import com.smashing.app.R.string.withdraw_deleted_matching_game_records
import com.smashing.app.R.string.withdraw_deleted_notifications_block_report
import com.smashing.app.R.string.withdraw_deleted_profile_account
import com.smashing.app.R.string.withdraw_deleted_reviews_ratings

enum class WithdrawalDeletedType(
    @StringRes val textResId: Int,
) {
    PROFILE_AND_ACCOUNT(withdraw_deleted_profile_account),
    MATCHING_AND_GAME_RECORDS(withdraw_deleted_matching_game_records),
    REVIEWS_AND_RATINGS(withdraw_deleted_reviews_ratings),
    CHAT_HISTORY(withdraw_deleted_chat_history),
    NOTIFICATIONS_AND_BLOCK_REPORT_HISTORY(withdraw_deleted_notifications_block_report),
}