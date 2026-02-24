package com.smashing.app.presentation.write.submit

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smashing.app.R
import com.smashing.app.R.string.submit_matching_result
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.dialog.SmashingDialog
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.DialogStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.clearFocus
import com.smashing.app.presentation.write.component.WriteResultContent

@Composable
fun SubmitResultRoute(
    navigateUp: () -> Unit,
    navigateToSubmitReview: () -> Unit,
    navigateToMatching: () -> Unit,
    viewModel: SubmitViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SubmitContract.SideEffect.NavigateToMatching -> navigateToMatching()
                }
            }
    }

    SubmitResultScreen(
        uiState = uiState,
        modifier = modifier,
        onBackClick = navigateUp,
        onLeftDoneClick = viewModel::updateSubmitterScore,
        onRightDoneClick = viewModel::updateReceiverScore,
        onWinnerSelected = viewModel::updateSelectedWinner,
        onNextClick = if (viewModel.isFirstAttempt) navigateToSubmitReview else viewModel::showResubmitDialog,
        onConfirmResubmit = viewModel::submitGame,
        onDismissResubmit = viewModel::hideResubmitDialog,
        leftTextFieldState = viewModel.leftTextFieldState,
        rightTextFieldState = viewModel.rightTextFieldState,
    )
}

@Composable
private fun SubmitResultScreen(
    uiState: SubmitContract.State,
    leftTextFieldState: TextFieldState,
    rightTextFieldState: TextFieldState,
    onBackClick: () -> Unit,
    onWinnerSelected: (String) -> Unit,
    onLeftDoneClick: (Int) -> Unit,
    onRightDoneClick: (Int) -> Unit,
    onNextClick: () -> Unit,
    onConfirmResubmit: () -> Unit,
    onDismissResubmit: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = SmashingTheme.colors.bgCanvas,
            )
            .systemBarsPadding()
            .clearFocus(focusManager),
    ) {
        SmashingDefaultTopBar(
            title = stringResource(submit_matching_result),
            topBarType = TopBarType.BACK,
            onClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
        ) {
            WriteResultContent(
                leftUserInfo = uiState.submitter,
                rightUserInfo = uiState.receiver,
                winnerId = uiState.winnerId,
                leftTextFieldState = leftTextFieldState,
                rightTextFieldState = rightTextFieldState,
                onWinnerSelected = onWinnerSelected,
                onLeftDoneClick = onLeftDoneClick,
                onRightDoneClick = onRightDoneClick,
                subTitle = "악의적인 결과 작성 시 활동이 제한될 수 있어요",
            )

            Spacer(modifier = Modifier.weight(1f))

            SmashingButton(
                buttonStyle = ButtonStyle.PRIMARY_WITH_DISABLED,
                text = stringResource(R.string.next),
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 48.dp),
                isEnabled = uiState.isButtonEnabled,
            )
        }

        if (uiState.isResubmitDialogVisible) {
            SmashingDialog(
                title = "매칭 결과를 다시 제출하시겠습니까?",
                subtitle = "상대가 다시 반려할 경우 매칭 기록은 삭제됩니다.",
                type = DialogStyle.ALERT,
                confirmText = "제출하기",
                dismissText = "아니요",
                onConfirmClick = onConfirmResubmit,
                onDismissClick = onDismissResubmit,
                onDismissRequest = onDismissResubmit,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubmitScreenPreview() {
    SubmitResultScreen(
        uiState = SubmitContract.State(),
        leftTextFieldState = TextFieldState(),
        rightTextFieldState = TextFieldState(),
        onBackClick = {},
        onLeftDoneClick = {},
        onRightDoneClick = {},
        onWinnerSelected = {},
        onNextClick = {},
        onConfirmResubmit = {},
        onDismissResubmit = {},
        modifier = Modifier
            .background(
                color = Color.Black,
            )
    )
}
