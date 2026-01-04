package com.smashing.app.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
/*
        Box(
            modifier = Modifier
                .size(70.dp, 50.dp)
                .background(color = Color.Black)
                .noRippleClickable(onClick = onKakaoLogoutClick),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "로그아웃",
                color = Color.White,
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .size(70.dp, 50.dp)
                .background(color = Color.Black)
                .noRippleClickable(onClick = onKakaoUnlinkClick),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "회원 탈퇴",
                color = Color.White,
            )
        }*/
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}
