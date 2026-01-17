package com.smashing.app.presentation.profile.addsports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smashing.app.R.string.sign_up_end_btn
import com.smashing.app.R.string.sign_up_next_btn
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.dialog.SmashingDialog
import com.smashing.app.core.designsystem.component.progressbar.SmashingProgressBar
import com.smashing.app.core.designsystem.component.sport.SportSelector
import com.smashing.app.core.designsystem.component.sport.SportSkillSelector
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.DialogStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.data.type.SkillType
import com.smashing.app.data.type.SportType
import kotlinx.collections.immutable.persistentListOf

private const val MAX_STEP = 2

@Composable
fun AddSportsRoute(
    navigateToUser: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddSportsViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is AddSportsUiState.AddSportsSideEffect.NavigateToSports -> navigateToUser()
                }
            }
    }

    AddSportScreen(
        uiState = uiState,
        selectedSport = uiState.addSportsInfo.selectedSports.firstOrNull(),
        selectedSkill = uiState.addSportsInfo.selectedSkill,
        isBtnEnabled = uiState.isBtnEnabled,
        onSportSelected = viewModel::updateSelectedSport,
        onSkillSelected = viewModel::updateSelectedSkill,
        onBackClick = navigateUp,
        modifier = modifier,
        onBtnClick = {
            if (uiState.currentStep < MAX_STEP)
                viewModel.updateCurrentStep()
            else {
                viewModel.postAddSport()
            }
        },
    )
}

@Composable
private fun AddSportScreen(
    uiState: AddSportsContract.State,
    selectedSport: SportType?,
    selectedSkill: SkillType?,
    isBtnEnabled: Boolean,
    onSportSelected: (SportType) -> Unit,
    onSkillSelected: (SkillType) -> Unit,
    onBtnClick: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    var showExitDialog by remember { mutableStateOf(false) }
    if (showExitDialog)
        SmashingDialog(
            title = "종목 추가를 취소하시겠습니까?",
            subtitle = "취소 시 진행중인 내용은 저장되지 않아요",
            type = DialogStyle.ALERT,
            confirmText = "취소하기",
            dismissText = "아니요",
            onConfirmClick = {
                showExitDialog = false
                onBackClick()
            },
            onDismissClick = { showExitDialog = false },
            onDismissRequest = { showExitDialog = false },
        )

    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SmashingDefaultTopBar(
            title = "종목 추가",
            topBarType = TopBarType.CLOSE,
            onClick = { showExitDialog = true },
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 48.dp),
        ) {
            SmashingProgressBar(
                progress = uiState.currentStep / MAX_STEP.toFloat(),
            )
            Spacer(modifier = Modifier.height(16.dp))

            when (uiState.currentStep) {

                1 -> SportSelector(
                    items = persistentListOf(
                        SportType.BADMINTON,
                        SportType.PING_PONG,
                        SportType.TENNIS,
                    ),
                    selectedSport = selectedSport,
                    onSportSelected = onSportSelected,
                    title = "추가할 종목을 선택해주세요",
                    subTitle = "",
                )

                2 -> SportSkillSelector(
                    selectedSkill = selectedSkill,
                    onSkillSelected = onSkillSelected,
                    title = "구력을 선택해주세요",
                    subTitle = "구력을 통해 임시 티어가 결정돼요",
                )

                else -> Spacer(modifier = Modifier.fillMaxSize())

            }

            Spacer(modifier = Modifier.weight(1f))

            SmashingButton(
                buttonStyle = ButtonStyle.PRIMARY_WITH_DISABLED,
                text = if (uiState.currentStep < MAX_STEP) {
                    stringResource(sign_up_next_btn)
                } else {
                    stringResource(sign_up_end_btn)
                },
                onClick = onBtnClick,
                modifier = Modifier.fillMaxWidth(),
                isEnabled = isBtnEnabled,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    SmashingAndroidTheme {
        var currentStep by rememberSaveable { mutableIntStateOf(1) }

        AddSportScreen(
            uiState = AddSportsContract.State(currentStep = currentStep),
            isBtnEnabled = true,
            selectedSport = null,
            onSportSelected = {},
            selectedSkill = null,
            onSkillSelected = {},
            onBtnClick = { currentStep ++ },
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
