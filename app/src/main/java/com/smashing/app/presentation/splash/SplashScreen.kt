package com.smashing.app.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.img_splash_logo
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors

private const val PADDING_RATIO = 202/304f

@Composable
fun SplashRoute(
    modifier: Modifier = Modifier,
) {

    SplashScreen(
        modifier = modifier,
    )
}

@Composable
private fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = colors.bgCanvas),
    ){
        Spacer(modifier = Modifier.weight(PADDING_RATIO))

        Image(
            painter = painterResource(img_splash_logo),
            contentDescription = null,
            modifier = Modifier
                .size(260.dp)
                .aspectRatio(1f)
                .padding(horizontal = 50.dp),
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}
