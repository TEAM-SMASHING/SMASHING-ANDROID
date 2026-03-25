package com.smashing.app.presentation.write.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.string.asterisk_label
import com.smashing.app.R.string.submit_title
import com.smashing.app.R.string.submit_winner
import com.smashing.app.core.designsystem.component.dropdown.SmashingWinnerDropdown
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.presentation.write.model.PlayerInfo
import kotlinx.collections.immutable.persistentListOf

@Composable
fun WriteResultContent(
    leftUserInfo: PlayerInfo,
    rightUserInfo: PlayerInfo,
    winnerId: String?,
    modifier: Modifier = Modifier,
    isContentEnabled: Boolean = true,
    title: String = stringResource(submit_title),
    subTitle: String? = null,
    onWinnerSelected: (String) -> Unit = {},
) {
    val dropDownList = persistentListOf(
        leftUserInfo.name,
        rightUserInfo.name,
    )

    val winnerName = when (winnerId) {
        rightUserInfo.profileId -> rightUserInfo.name
        leftUserInfo.profileId -> leftUserInfo.name
        else -> null
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = title,
            color = SmashingTheme.colors.txtPrimary,
            style = SmashingTheme.typography.xl.semibold20,
        )

        subTitle?.let {
            Text(
                text = subTitle,
                color = SmashingTheme.colors.txtTertiary,
                style = SmashingTheme.typography.sm.medium14,
            )
        }

        SubmitCard(
            leftUser = leftUserInfo,
            rightUser = rightUserInfo,
            winnerId = winnerId,
            modifier = Modifier.padding(top = 28.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AccentAsteriskLabel(
                text = stringResource(submit_winner),
            )

            SmashingWinnerDropdown(
                selectedItem = winnerName,
                items = dropDownList,
                onClick = onWinnerSelected,
                enabled = isContentEnabled,
            )
        }
    }
}

@Composable
private fun AccentAsteriskLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = buildAnnotatedString {
            append(text)

            withStyle(
                style = SpanStyle(color = SmashingTheme.colors.txtRed),
            ) {
                append(" ")
                append(stringResource(asterisk_label))
            }
        },
        style = SmashingTheme.typography.md.medium16,
        color = SmashingTheme.colors.txtPrimary,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun WriteResultPreview() {
    SmashingAndroidTheme {
        WriteResultContent(
            modifier = Modifier.background(color = Color.Black),
            rightUserInfo = PlayerInfo(profileId = "1", name = "Submitter"),
            leftUserInfo = PlayerInfo(profileId = "2", name = "Receiver"),
            winnerId = "1",
            onWinnerSelected = {},
        )
    }
}
