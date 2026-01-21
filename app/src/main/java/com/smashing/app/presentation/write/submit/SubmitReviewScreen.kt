package com.smashing.app.presentation.write.submit

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.dialog.SmashingDialog
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.DialogStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.clearFocus
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType
import com.smashing.app.presentation.write.component.WriteReviewContent

@Composable
fun SubmitReviewRoute(
    navigateUp: () -> Unit,
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

    SubmitReviewScreen(
        uiState = uiState,
        reviewTextFieldState = viewModel.reviewTextFieldState,
        onBackClick = navigateUp,
        onDoneClick = viewModel::submitGame,
        onReviewRatingClick = viewModel::updateSelectedRatingType,
        onReviewTagClick = viewModel::updateSelectedTagType,
        isButtonEnabled = uiState.isButtonEnabled,
        modifier = modifier,
    )
}

@Composable
private fun SubmitReviewScreen(
    uiState: SubmitContract.State,
    reviewTextFieldState: TextFieldState,
    onReviewRatingClick: (ReviewRatingType) -> Unit,
    onReviewTagClick: (ReviewTagType) -> Unit,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    isButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
) {
    val focusManager = LocalFocusManager.current

    var isAlertDialogOpen by remember { mutableStateOf(false) }
    var isConfirmDialogOpen by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas)
            .systemBarsPadding()
            .clearFocus(focusManager),
    ) {
        SmashingDefaultTopBar(
            title = "후기 작성",
            topBarType = TopBarType.BACK,
            onClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            WriteReviewContent(
                nickname = uiState.receiver.name,
                textFieldState = reviewTextFieldState,
                selectedReviewRating = uiState.selectedRating,
                selectedReviewTagTypes = uiState.selectedTagList,
                onReviewRatingClick = onReviewRatingClick,
                onReviewTagClick = onReviewTagClick,
            )

            SmashingButton(
                buttonStyle = ButtonStyle.PRIMARY_WITH_DISABLED,
                text = "완료",
                onClick = onDoneClick,
                isEnabled = isButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 13.dp,
                        bottom = 48.dp,
                    ),
            )
        }

        if (isAlertDialogOpen) {
            SmashingDialog(
                title = "매칭 결과를 제출하시겠습니까?",
                subtitle = "정확한 경기 결과각 아닐 경우 반려될 수 있어요.",
                type = DialogStyle.ALERT,
                confirmText = "제출하기",
                dismissText = "아니요",
                onDismissRequest = { isAlertDialogOpen = false },
                onConfirmClick = onDoneClick, // TODO: 제출하기
            )
        }

        if (isConfirmDialogOpen) {
            SmashingDialog(
                title = "매칭 결과를 제출하시겠습니까?",
                subtitle = "정확한 경기 결과가 아닐 경우 반려될 수 있어요.",
                type = DialogStyle.CONFIRM,
                confirmText = "확인",
                onDismissRequest = { isConfirmDialogOpen = false },
                onConfirmClick = onDoneClick, // TODO: 제출하기
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubmitReviewScreenPreview() {
    SmashingAndroidTheme {
        SubmitReviewScreen(
            uiState = SubmitContract.State(),
            reviewTextFieldState = TextFieldState(),
            onBackClick = {},
            onDoneClick = {},
            isButtonEnabled = true,
            onReviewTagClick = {},
            onReviewRatingClick = {},
            modifier = Modifier,
        )
    }
}
