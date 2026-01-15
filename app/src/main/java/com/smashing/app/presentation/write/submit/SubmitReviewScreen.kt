package com.smashing.app.presentation.write.submit

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.presentation.write.component.WriteReviewContent

@Composable
fun SubmitReviewRoute(
    navigateUp: () -> Unit,
    navigateToMatching: () -> Unit,
    viewModel: SubmitViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SubmitReviewScreen(
        uiState = uiState,
        reviewTextFieldState = viewModel.reviewTextFieldState,
        onBackClick = navigateUp,
        onDoneClick = navigateToMatching,
        isButtonEnabled = uiState.isButtonEnabled,
        modifier = modifier,
    )
}

@Composable
private fun SubmitReviewScreen(
    uiState: SubmitContract.State,
    reviewTextFieldState: TextFieldState,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    isButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
) {
    Column(
        modifier = modifier,
    ) {
        SmashingDefaultTopBar(
            title = "후기 작성",
            topBarType = TopBarType.BACK,
            onClick = onBackClick,
            modifier = modifier,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
        ) {
            WriteReviewContent(
                nickname = uiState.receiver.name,
                textFieldState = reviewTextFieldState,
                selectedCardItems = uiState.selectedRatingTypes,
                onCardItemClick = {},
            )

            Spacer(Modifier.weight(1f))

            SmashingButton(
                buttonStyle = ButtonStyle.PRIMARY_WITH_DISABLED,
                text = "완료",
                onClick = onDoneClick,
                isEnabled = isButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 48.dp,
                    ),
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
            modifier = Modifier,
        )
    }
}
