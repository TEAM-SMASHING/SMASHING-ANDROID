package com.smashing.app.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.smashing.app.R
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.presentation.login.LoginContract.SideEffect.NavigateToHome
import com.smashing.app.presentation.login.LoginContract.SideEffect.NavigateToSignUp
import com.smashing.app.presentation.login.component.KakaoLoginButton
import kotlinx.coroutines.launch


private const val LOGO_RATIO = 261 / 112f
private const val PADDING_RATIO = 204 / 275f

@Composable
fun LoginRoute(
    navigateToSignUp: (String) -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToHome -> navigateToHome()
                    is NavigateToSignUp -> navigateToSignUp(sideEffect.kakaoId)
                }
            }
    }

    LoginScreen(
        onKakaoLoginClick = {
            lifecycleOwner.lifecycleScope.launch {
                SocialLoginManager(context = context, viewModel = viewModel).loginKakao()
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun LoginScreen(
    onKakaoLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.bgCanvas)
            .padding(horizontal = 16.dp)
            .padding(
                bottom = 125.dp,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(PADDING_RATIO))

        Image(
            painter = painterResource(R.drawable.img_login_logo),
            contentDescription = null,
            modifier = Modifier
                .width(261.dp)
                .aspectRatio(LOGO_RATIO),
        )

        Spacer(modifier = Modifier.weight(1f))

        KakaoLoginButton(
            onLoginBtnClick = onKakaoLoginClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        onKakaoLoginClick = {},
    )
}
