package com.smashing.app.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.smashing.app.R
import com.smashing.app.core.extension.noRippleClickable

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
) {
    LoginScreen(
        modifier = modifier,
        onKakaoLoginClick = {},
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    onKakaoLoginClick: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.img_kakao_login),
            contentDescription = null,
            modifier = Modifier.noRippleClickable(onClick = onKakaoLoginClick),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}
