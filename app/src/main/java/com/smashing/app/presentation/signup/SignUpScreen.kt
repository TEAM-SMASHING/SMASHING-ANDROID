package com.smashing.app.presentation.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.string.sign_up_next_btn
import com.smashing.app.R.string.sign_up_end_btn
import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.progressbar.SmashingProgressBar
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.presentation.signup.component.SignUpFinish
import com.smashing.app.presentation.signup.component.chatlink.SignUpChatLink
import com.smashing.app.presentation.signup.component.gender.SignUpGender
import com.smashing.app.presentation.signup.component.location.SignUpLocation
import com.smashing.app.presentation.signup.component.nickname.SignUpNickName
import com.smashing.app.core.designsystem.component.onboarding.skill.SignUpSkill
import com.smashing.app.core.designsystem.component.onboarding.sport.SignUpSport
import kotlinx.collections.immutable.persistentListOf

private const val MAX_STEP = 6

@Composable
fun SignUpRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SignUpScreen(
        uiState = uiState,
        onBackClick = {},
        modifier = modifier,
        onBtnClick = {
            if (uiState.currentStep < MAX_STEP)
                viewModel.updateCurrentStep()
            else {
                viewModel.postSignUp(navigateToHome)
            }
        },
    )
}

@Composable
private fun SignUpScreen(
    uiState: SignUpContract.State,
    onBackClick: () -> Unit,
    onBtnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                bottom = 48.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SmashingDefaultTopBar(
            title = "",
            topBarType = TopBarType.BACK,
            onClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
        ) {

            if (uiState.currentStep < MAX_STEP+1){
                SmashingProgressBar(
                    progress = uiState.currentStep / MAX_STEP.toFloat(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                when (uiState.currentStep) {
                    1 -> SignUpNickName(
                        nickNameState = uiState.nicknameInput,
                        onDuplicateBtnClick = { },
                    )
                    2 -> SignUpGender()
                    3 -> SignUpChatLink()
                    4 -> SignUpSport(
                        items = persistentListOf(
                            SportType.BADMINTON,
                            SportType.PING_PONG,
                            SportType.TENNIS,
                        ),
                    )
                    5 -> SignUpSkill()
                    else -> SignUpLocation(
                        onAddressClick = {},
                    )
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))

                SignUpFinish(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally),
                )
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
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    SmashingAndroidTheme {
        var currentStep by rememberSaveable { mutableIntStateOf(1) }
        val progress = when (currentStep) {
            1 -> 0.17f
            2 -> 0.34f
            3 -> 0.51f
            4 -> 0.64f
            5 -> 0.81f
            else -> 1f
        }
        SignUpScreen(
            uiState = SignUpContract.State(),
            onBackClick = {},
            onBtnClick = {currentStep = currentStep + 1},
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
