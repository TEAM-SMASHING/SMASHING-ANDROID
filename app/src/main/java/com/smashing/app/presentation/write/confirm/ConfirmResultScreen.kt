package com.smashing.app.presentation.write.confirm

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.string.confirm_result
import com.smashing.app.core.designsystem.component.bottomsheet.SmashingBottomSheet
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.dialog.SmashingDialog
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.DialogStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.presentation.write.component.WriteResultContent
import com.smashing.app.presentation.write.confirm.type.ConfirmDenyType
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ConfirmResultRoute(
    navigateUp: () -> Unit,
    navigateToConfirmReview: () -> Unit,
    viewModel: ConfirmViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConfirmResultScreen(
        uiState = uiState,
        isFirstAttempt = viewModel.isFirstAttempt,
        leftTextFieldState = viewModel.leftTextFieldState,
        rightTextFieldState = viewModel.rightTextFieldState,
        onBackClick = navigateUp,
        onConfirmClick = navigateToConfirmReview,
        onShowResubmitDialog = viewModel::showResubmitDialog,
        onHideResubmitDialog = viewModel::hideResubmitDialog,
        onDenyClick = viewModel::denySubmission,
        modifier = modifier,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConfirmResultScreen(
    uiState: ConfirmContract.State,
    isFirstAttempt: Boolean,
    leftTextFieldState: TextFieldState,
    rightTextFieldState: TextFieldState,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onShowResubmitDialog: () -> Unit,
    onHideResubmitDialog: () -> Unit,
    onDenyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
) {
    var showExitBottomSheet by remember { mutableStateOf(false) }
    var selectedReason by remember { mutableStateOf("") }
    val bottomSheetItems = ConfirmDenyType.entries.map { it.description }.toPersistentList()


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = SmashingTheme.colors.bgCanvas,
            )
            .systemBarsPadding(),
    ) {
        SmashingDefaultTopBar(
            title = stringResource(confirm_result),
            topBarType = TopBarType.BACK,
            onClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
        ) {
            WriteResultContent(
                submitter = uiState.submitter,
                receiver = uiState.receiver,
                winnerId = uiState.winnerId,
                leftTextFieldState = leftTextFieldState,
                rightTextFieldState = rightTextFieldState,
                isTextFieldsEnabled = false,
                title = "경기 결과를 확인해주세요",
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "경기 결과가 일치하나요?",
                style = SmashingTheme.typography.sm.regular14,
                color = SmashingTheme.colors.txtPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 48.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SmashingButton(
                    buttonStyle = ButtonStyle.DISABLED_ACTIVE,
                    text = "아니요",
                    modifier = Modifier.weight(131f),
                    onClick = { showExitBottomSheet = true },
                )
                SmashingButton(
                    buttonStyle = ButtonStyle.PRIMARY,
                    text = "네, 맞아요",
                    modifier = Modifier.weight(185f),
                    onClick = { if (isFirstAttempt) onConfirmClick() else onShowResubmitDialog() },
                )
            }
        }

        if (showExitBottomSheet) {
            SmashingBottomSheet(
                onDismissRequest = {
                    showExitBottomSheet = false
                    selectedReason = ""
                },
                title = "어떤 내용이 잘못됐나요?",
                items = bottomSheetItems,
                selectedItem = selectedReason,
                contentToBtnPadding = 20.dp,
                btnText = "완료",
                onItemClick = { selectedReason = it },
                onBtnClick = {
                    showExitBottomSheet = false
                    if (selectedReason.isNotEmpty()) {
                        onDenyClick(selectedReason)
                    }
                },
            )
        }

        if (uiState.isResubmitDialogVisible) {
            SmashingDialog(
                title = "매칭 결과를 다시 제출하시겠습니까?",
                subtitle = "상대가 다시 반려할 경우 매칭 기록은 삭제됩니다.",
                confirmText = "제출하기",
                dismissText = "아니요",
                onDismissRequest = onHideResubmitDialog,
                type = DialogStyle.ALERT,
                onConfirmClick = onConfirmClick,
                onDismissClick = onHideResubmitDialog,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmResultScreenPreview() {
    SmashingAndroidTheme {
        ConfirmResultScreen(
            uiState = ConfirmContract.State(),
            isFirstAttempt = true,
            leftTextFieldState = rememberTextFieldState(3.toString()),
            rightTextFieldState = rememberTextFieldState(1.toString()),
            onBackClick = {},
            onConfirmClick = {},
            onShowResubmitDialog = {},
            onHideResubmitDialog = {},
            onDenyClick = {},
        )
    }
}
