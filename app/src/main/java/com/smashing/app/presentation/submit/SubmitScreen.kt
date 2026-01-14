package com.smashing.app.presentation.submit

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R
import com.smashing.app.R.string.asterisk_label
import com.smashing.app.R.string.score_separator
import com.smashing.app.R.string.submit_matching_result
import com.smashing.app.R.string.submit_score
import com.smashing.app.R.string.submit_winner
import com.smashing.app.R.string.zero_label
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.dropdown.SmashingWinnerDropdown
import com.smashing.app.core.designsystem.component.textfield.ScoreInputTextField
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.intValue
import com.smashing.app.presentation.submit.component.SubmitScoreCard
import com.smashing.app.presentation.submit.model.MatchPlayer
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SubmitRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SubmitViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SubmitScreen(
        uiState = uiState,
        modifier = modifier,
        onBackClick = navigateUp,
        onLeftDoneClick = viewModel::updateSubmitterScore,
        onRightDoneClick = viewModel::updateReceiverScore,
        onWinnerSelected = viewModel::updateSelectedWinner,
        leftTextFieldState = viewModel.leftTextFieldState,
        rightTextFieldState = viewModel.rightTextFieldState,
    )
}

@Composable
private fun SubmitScreen(
    uiState: SubmitContract.State,
    leftTextFieldState: TextFieldState,
    rightTextFieldState: TextFieldState,
    onBackClick: () -> Unit,
    onWinnerSelected: (String) -> Unit,
    onLeftDoneClick: (Int) -> Unit,
    onRightDoneClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
) {
    val dropDownList = persistentListOf(
        uiState.submitter.name,
        uiState.receiver.name,
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        SmashingDefaultTopBar(
            title = stringResource(submit_matching_result),
            topBarType = TopBarType.BACK,
            onClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
        ) {
            Text(
                text = stringResource(R.string.submit_title),
                color = SmashingTheme.colors.txtPrimary,
                style = SmashingTheme.typography.xl.semibold20,
            )

            Text(
                text = stringResource(R.string.submit_description),
                color = SmashingTheme.colors.txtTertiary,
                style = SmashingTheme.typography.sm.medium14,
            )

            SubmitScoreCard(
                submitter = uiState.submitter,
                submitterScore = uiState.submitterScore,
                receiver = uiState.receiver,
                receiverScore = uiState.receiverScore,
                winner = uiState.winner,
                modifier = Modifier.padding(top = 28.dp),
            )

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
            ) {
                val (winnerLabel, scoreLabel, winnerDropdown, scoreRow) = createRefs()

                SmashingWinnerDropdown(
                    selectedItem = uiState.winner?.name,
                    items = dropDownList,
                    onClick = onWinnerSelected,
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

        Spacer(Modifier.weight(1f))

        SmashingButton(
            buttonStyle = ButtonStyle.PRIMARY_WITH_DISABLED,
            text = stringResource(R.string.next),
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 48.dp),
            isEnabled = uiState.isButtonEnabled,
        )
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
private fun SubmitScreenPreview() {
    SubmitScreen(
        uiState = SubmitContract.State(
            submitter = MatchPlayer(userId = "1", name = "밤이달이"),
            submitterScore = 10,
            receiver = MatchPlayer(userId = "2", name = "와쿠와쿠"),
            receiverScore = 5,
        ),
        leftTextFieldState = TextFieldState(),
        rightTextFieldState = TextFieldState(),
        onBackClick = {},
        onLeftDoneClick = {},
        onRightDoneClick = {},
        onWinnerSelected = {},
        modifier = Modifier
            .background(
                color = Color.Black,
            )
    )
}
