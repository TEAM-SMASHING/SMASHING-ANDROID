package com.smashing.app.presentation.addsports

import com.smashing.app.R.string.cancel
import com.smashing.app.R.string.no
import com.smashing.app.R.string.addsports_select_sport
import com.smashing.app.R.string.addsports_select_experience
import com.smashing.app.R.string.addsports_temporary_tier_determination
import com.smashing.app.R.string.dialog_cancel_add_item_title
import com.smashing.app.R.string.dialog_cancel_add_item_message
import com.smashing.app.R.string.addsports_title
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
import com.smashing.app.core.designsystem.state.TopBarState
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.data.type.SkillType
import com.smashing.app.data.type.SportType
import kotlinx.collections.immutable.toPersistentList

private const val MAX_STEP = 2

@Composable
fun AddSportsRoute(
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
                    is AddSportsUiState.AddSportsSideEffect.NavigateToSports -> navigateUp()
                }
            }
    }

    AddSportScreen(
        uiState = uiState,
        selectedSport = uiState.addSportsInfo.selectedSports,
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
            title = stringResource(dialog_cancel_add_item_title),
            onDismissClick = { showExitDialog = false },
            subtitle = stringResource(dialog_cancel_add_item_message),
            type = DialogStyle.ALERT,
            confirmText = stringResource(cancel),
            dismissText = stringResource(no),
            onConfirmClick = {
                showExitDialog = false
                onBackClick()
            },
        )

    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SmashingDefaultTopBar(
            state = TopBarState.Close(
                title = stringResource(addsports_title),
                onCloseClick = { showExitDialog = true },
            ),
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
                    items = uiState.availableSports.toPersistentList(),
                    selectedSport = selectedSport,
                    onSportSelected = onSportSelected,
                    title = stringResource(addsports_select_sport),
                    subTitle = "",
                    isSubTitle = false,
                )

                2 -> SportSkillSelector(
                    selectedSkill = selectedSkill,
                    onSkillSelected = onSkillSelected,
                    title = stringResource(addsports_select_experience),
                    subTitle = stringResource(addsports_temporary_tier_determination),
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
            onSportSelected ={},
            selectedSkill = null,
            onSkillSelected = {},
            onBtnClick = { currentStep++ },
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
