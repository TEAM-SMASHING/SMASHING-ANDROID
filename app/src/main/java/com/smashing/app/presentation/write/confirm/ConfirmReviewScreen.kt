package com.smashing.app.presentation.write.confirm

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.dialog.SmashingDialog
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.DialogStyle
import com.smashing.app.core.designsystem.style.TopBarStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.clearFocus
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType
import com.smashing.app.presentation.write.component.WriteReviewContent
import com.smashing.app.presentation.write.confirm.ConfirmContract.SideEffect.ConfirmReviewSideEffect
import kotlinx.coroutines.flow.filterIsInstance

@Composable
fun ConfirmReviewRoute(
    navigateUp: () -> Unit,
    navigateToConfirmReview: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConfirmViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .filterIsInstance<ConfirmReviewSideEffect>()
            .collect { sideEffect ->
                when (sideEffect) {
                    is ConfirmReviewSideEffect.NavigateBack -> navigateUp()
                    is ConfirmReviewSideEffect.NavigateToConfirmReview -> {
                        navigateToConfirmReview(sideEffect.reviewId)
                    }
                }
            }
    }

    ConfirmReviewScreen(
        uiState = uiState,
        reviewTextFieldState = viewModel.reviewTextFieldState,
        onBackClick = navigateUp,
        onShowConfirmDialog = viewModel::showConfirmDialog,
        onHideConfirmDialog = viewModel::hideConfirmDialog,
        onConfirmSubmission = viewModel::confirmSubmission,
        onReviewRatingClick = viewModel::updateSelectedRatingType,
        onReviewTagClick = viewModel::updateSelectedTagType,
        modifier = modifier,
    )
}

@Composable
private fun ConfirmReviewScreen(
    uiState: ConfirmContract.State,
    reviewTextFieldState: TextFieldState,
    onReviewRatingClick: (ReviewRatingType) -> Unit,
    onReviewTagClick: (ReviewTagType) -> Unit,
    onBackClick: () -> Unit,
    onShowConfirmDialog: () -> Unit,
    onHideConfirmDialog: () -> Unit,
    onConfirmSubmission: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
) {
    val focusManager = LocalFocusManager.current
    val isButtonEnabled = uiState.selectedRating != null

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas)
            .systemBarsPadding()
            .clearFocus(focusManager),
    ) {
        SmashingDefaultTopBar(
            title = "후기 작성",
            topBarStyle = TopBarStyle.BACK,
            onBackClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .imePadding()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            WriteReviewContent(
                nickname = uiState.submitter.name,
                textFieldState = reviewTextFieldState,
                selectedReviewRating = uiState.selectedRating,
                selectedReviewTagTypes = uiState.selectedTagList,
                onReviewRatingClick = onReviewRatingClick,
                onReviewTagClick = onReviewTagClick,
            )

            Spacer(modifier = Modifier.weight(1f))

            SmashingButton(
                buttonStyle = ButtonStyle.PRIMARY_WITH_DISABLED,
                text = "완료",
                onClick = onShowConfirmDialog,
                isEnabled = isButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 13.dp,
                        bottom = 48.dp,
                    ),
            )
        }

        if (uiState.showConfirmDialog) {
            SmashingDialog(
                title = "매칭 결과를 확정하시겠습니까?",
                onDismissClick = onHideConfirmDialog,
                subtitle = "한 번 확정하면 수정할 수 없어요.",
                type = DialogStyle.ALERT,
                confirmText = "제출하기",
                dismissText = "아니요",
                onConfirmClick = onConfirmSubmission,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmReviewScreenPreview() {
    SmashingAndroidTheme {
        ConfirmReviewScreen(
            uiState = ConfirmContract.State(),
            reviewTextFieldState = TextFieldState(),
            onBackClick = {},
            onShowConfirmDialog = {},
            onHideConfirmDialog = {},
            onConfirmSubmission = {},
            onReviewTagClick = {},
            onReviewRatingClick = {},
            modifier = Modifier,
        )
    }
}
