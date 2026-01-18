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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.string.confirm_result
import com.smashing.app.core.designsystem.component.bottomsheet.SmashingBottomSheet
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
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
    navigateToMatching: () -> Unit,
    viewModel: ConfirmViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConfirmResultScreen(
        uiState = uiState,
        leftTextFieldState = viewModel.leftTextFieldState,
        rightTextFieldState = viewModel.rightTextFieldState,
        onBackClick = navigateUp,
        onWinnerSelected = viewModel::updateSelectedWinner,
        onLeftDoneClick = viewModel::updateSubmitterScore,
        onRightDoneClick = viewModel::updateReceiverScore,
        onConfirmClick = navigateToConfirmReview,
        onDenyClick = navigateToMatching,
        modifier = modifier,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConfirmResultScreen(
    uiState: ConfirmContract.State,
    leftTextFieldState: TextFieldState,
    rightTextFieldState: TextFieldState,
    onBackClick: () -> Unit,
    onWinnerSelected: (String) -> Unit,
    onLeftDoneClick: (Int) -> Unit,
    onRightDoneClick: (Int) -> Unit,
    onConfirmClick: () -> Unit,
    onDenyClick: () -> Unit,
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
                submitterScore = uiState.submitterScore,
                receiverScore = uiState.receiverScore,
                winner = uiState.winner,
                leftTextFieldState = leftTextFieldState,
                rightTextFieldState = rightTextFieldState,
                onWinnerSelected = onWinnerSelected,
                onLeftDoneClick = onLeftDoneClick,
                onRightDoneClick = onRightDoneClick,
                isTextFieldsEnabled = false,
            )

            Spacer(modifier = Modifier.weight(1f))
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
                    onClick = onConfirmClick,
                )
            }
        }
        if (showExitBottomSheet) {
            SmashingBottomSheet(
                onDismissRequest = {
                    showExitBottomSheet = false
                },
                title = "매칭결과",
                items = bottomSheetItems,
                selectedItem = selectedReason,
                contentToBtnPadding = 20.dp,
                btnText = "완료",
                onItemClick = { selectedReason = it },
                onBtnClick = {
                    showExitBottomSheet = false
                    if (selectedReason.isNotEmpty()) {
                        onDenyClick()
                    }
                },
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
            leftTextFieldState = rememberTextFieldState(3.toString()),
            rightTextFieldState = rememberTextFieldState(1.toString()),
            onBackClick = {},
            onWinnerSelected = {},
            onLeftDoneClick = {},
            onRightDoneClick = {},
            onConfirmClick = {},
            onDenyClick = {},
        )
    }
}
