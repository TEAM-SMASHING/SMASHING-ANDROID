package com.smashing.app.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smashing.app.R.drawable.img_kakao_login
import com.smashing.app.core.extension.noRippleClickable

@Composable
fun LoginRoute(
    innerPadding: PaddingValues,
    navigateToSignUp: (String) -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LoginScreen(
        innerPadding = innerPadding,
        onKakaoLoginClick = {
            viewModel.postKakaoLogin(
                context = context,
                onKakaoLoginSuccess = navigateToSignUp,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun LoginScreen(
    innerPadding: PaddingValues,
    onKakaoLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    // TODO: 추후 수정 예정
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = img_kakao_login),
            contentDescription = null,
            modifier = Modifier.noRippleClickable(
                onClick = { onKakaoLoginClick },
            ),
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        innerPadding = PaddingValues(),
        onKakaoLoginClick = {},
    )
}
