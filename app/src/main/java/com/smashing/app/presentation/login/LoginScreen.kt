package com.smashing.app.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R
import com.smashing.app.core.extension.noRippleClickable

@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    LoginScreen(
        modifier = modifier,
        onKakaoLoginClick = { KakaoLoginManager(context).logInKakao(navigateToHome) },
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    onKakaoLoginClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.img_kakao_login),
            contentDescription = null,
            modifier = Modifier.noRippleClickable(onClick = onKakaoLoginClick),
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}
