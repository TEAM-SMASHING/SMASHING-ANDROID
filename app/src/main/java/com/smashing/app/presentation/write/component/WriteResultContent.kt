package com.smashing.app.presentation.write.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.smashing.app.R.string.asterisk_label
import com.smashing.app.R.string.score_separator
import com.smashing.app.R.string.submit_score
import com.smashing.app.R.string.submit_title
import com.smashing.app.R.string.submit_winner
import com.smashing.app.R.string.zero_label
import com.smashing.app.R.string.submit_description
import com.smashing.app.core.designsystem.component.dropdown.SmashingWinnerDropdown
import com.smashing.app.core.designsystem.component.textfield.ScoreInputTextField
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.intValue
import com.smashing.app.presentation.write.model.PlayerInfo
import kotlinx.collections.immutable.persistentListOf

@Composable
fun WriteResultContent(
    submitter: PlayerInfo,
    receiver: PlayerInfo,
    winnerId: String?,
    leftTextFieldState: TextFieldState,
    rightTextFieldState: TextFieldState,
    modifier: Modifier = Modifier,
    isTextFieldsEnabled: Boolean = true,
    title: String = stringResource(submit_title),
    subTitle: String? = null,
    onWinnerSelected: (String) -> Unit = {},
    onLeftDoneClick: (Int) -> Unit = {},
    onRightDoneClick: (Int) -> Unit = {},
) {
    val dropDownList = persistentListOf(
        submitter.name,
        receiver.name,
    )

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

        SubmitScoreCard(
            submitter = submitter,
            receiver = receiver,
            winnerId = winnerId,
            modifier = Modifier.padding(top = 28.dp),
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
        ) {
            val (winnerLabel, scoreLabel, winnerDropdown, scoreRow) = createRefs()

            val winnerName = when (winnerId) {
                submitter.userId -> submitter.name
                receiver.userId -> receiver.name
                else -> null
            }

            SmashingWinnerDropdown(
                selectedItem = winnerName,
                items = dropDownList,
                onClick = onWinnerSelected,
                enabled = isTextFieldsEnabled,
                modifier = Modifier.constrainAs(winnerDropdown) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.wrapContent
                }
            )

            AccentAsteriskLabel(
                text = stringResource(submit_winner),
                modifier = Modifier.constrainAs(winnerLabel) {
                    start.linkTo(parent.start)
                    top.linkTo(winnerDropdown.top)
                    bottom.linkTo(winnerDropdown.bottom)
                    width = Dimension.wrapContent
                }
            )

            Row(
                modifier = Modifier
                    .constrainAs(scoreRow) {
                        end.linkTo(winnerDropdown.end)
                        start.linkTo(winnerDropdown.start)
                        top.linkTo(winnerDropdown.bottom, 20.dp)
                        width = Dimension.fillToConstraints
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ScoreInputTextField(
                    state = leftTextFieldState,
                    placeholder = stringResource(zero_label),
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 10.dp),
                    onDoneClick = {
                        onLeftDoneClick(leftTextFieldState.intValue)
                    },
                    isEnabled = isTextFieldsEnabled,
                )

                Text(
                    text = stringResource(score_separator),
                    color = SmashingTheme.colors.txtTertiary,
                    style = SmashingTheme.typography.md.medium16,
                    modifier = Modifier.padding(horizontal = 12.dp),
                )

                ScoreInputTextField(
                    state = rightTextFieldState,
                    placeholder = stringResource(zero_label),
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 10.dp),
                    onDoneClick = {
                        onRightDoneClick(rightTextFieldState.intValue)
                    },
                    isEnabled = isTextFieldsEnabled,
                )
            }

            AccentAsteriskLabel(
                text = stringResource(submit_score),
                modifier = Modifier.constrainAs(scoreLabel) {
                    top.linkTo(scoreRow.top)
                    bottom.linkTo(scoreRow.bottom)
                    width = Dimension.wrapContent
                }
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
            submitter = PlayerInfo(userId = "1", name = "Submitter", score = 4),
            receiver = PlayerInfo(userId = "2", name = "Receiver", score = 5),
            winnerId = "1",
            leftTextFieldState = TextFieldState(),
            rightTextFieldState = TextFieldState(),
            onWinnerSelected = {},
            onLeftDoneClick = {},
            onRightDoneClick = {},
        )
    }
}
