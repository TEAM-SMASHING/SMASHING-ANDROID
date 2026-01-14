package com.smashing.app.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smashing.app.R.drawable.img_logo
import com.smashing.app.R.string.login_description_s
import com.smashing.app.R.string.login_description_for_ports
import com.smashing.app.R.string.login_description_m
import com.smashing.app.R.string.login_description_athching
import com.smashing.app.R.string.login_description_ing
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.presentation.login.component.KakaoLoginButton

private const val LOGO_RATIO = 240/80f

@Composable
fun LoginRoute(
    navigateToSignUp: (String) -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    LoginScreen(
        onKakaoLoginClick = {
            viewModel.postKakaoLogin(
                context = context,
                onSignedUpUserLoginSuccess = navigateToHome,
                onNewUserLoginSuccess = navigateToSignUp,
            )
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
            .padding(horizontal = 16.dp)
            .padding(
                bottom = 125.dp,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(0.74f))

        Image(
            painter = painterResource(img_logo),
            contentDescription = null,
            modifier = Modifier
                .width(240.dp)
                .aspectRatio(LOGO_RATIO)
                .noRippleClickable(
                    onClick = { onKakaoLoginClick }
                ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = colors.txtEmphasis)) {
                    append(stringResource(login_description_s))
                }

                append(stringResource(login_description_for_ports))

                withStyle(style = SpanStyle(color = colors.txtEmphasis)) {
                    append(stringResource(login_description_m))
                }

                append(stringResource(login_description_athching))

                withStyle(style = SpanStyle(color = colors.txtEmphasis)) {
                    append(stringResource(login_description_ing))
                }
            },
            color = colors.txtSecondary,
            style = typography.md.semibold16,
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
