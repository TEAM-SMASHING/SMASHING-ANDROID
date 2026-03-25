package com.smashing.app.presentation.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.img_profile
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.model.review.GameReview

@Composable
fun ReviewItem(
    review: GameReview,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        UrlImage(
            placeholderDrawable = img_profile,
            modifier = Modifier
                .height(32.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = review.opponentNickname,
                    style = SmashingTheme.typography.sm.semibold14,
                    color = SmashingTheme.colors.txtPrimary,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = review.createdAt,
                    style = SmashingTheme.typography.xs.medium12,
                    color = SmashingTheme.colors.txtTertiary,
                )
            }
            Text(
                text = review.content ?: "",
                style = SmashingTheme.typography.sm.medium14,
                color = SmashingTheme.colors.txtPrimary,
            )
        }
    }
}
