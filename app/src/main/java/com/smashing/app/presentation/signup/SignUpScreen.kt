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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.progressbar.SmashingProgressBar
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.presentation.signup.component.chatlink.SignUpChatLink
import com.smashing.app.presentation.signup.component.gender.SignUpGender
import com.smashing.app.presentation.signup.component.nickname.SignUpNickName
import com.smashing.app.presentation.signup.component.skill.SignUpSkill
import com.smashing.app.presentation.signup.component.sport.SignUpSport
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SignUpRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {

    SignUpScreen(
        onSignupClick = {
            viewModel.postSignUp(
                onSignupSuccess = navigateToHome,
            )
        },
        onBackClick = {},
        modifier = modifier,
    )
}

@Composable
private fun SignUpScreen(
    onSignupClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    var currentStep by rememberSaveable { mutableIntStateOf(1) }
    val progress = when (currentStep) {
        1 -> 0.17f
        2 -> 0.34f
        3 -> 0.51f
        4 -> 0.64f
        5 -> 0.81f
        else -> 1f
    }

    // TODO: 추후 수정 예정
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

            SmashingProgressBar(
                progress = progress,
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (currentStep) {
                1 -> SignUpNickName(
                    onDuplicateBtnClick = { }
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
                6 -> SignUpNickName(
                    onDuplicateBtnClick = { }
                )//Todo 지역
            }

            Spacer(modifier = Modifier.weight(1f))

            SmashingButton(
                buttonStyle = ButtonStyle.PRIMARY_WITH_DISABLED,
                text = "다음",
                onClick = {
                    onSignupClick
                    currentStep = currentStep + 1
                },
                modifier = Modifier.fillMaxWidth()

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    SmashingAndroidTheme {
        SignUpScreen(
            onSignupClick = {},
            onBackClick = {},
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
