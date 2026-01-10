package com.smashing.app.presentation.matching

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme

@Composable
fun MatchingRoute(
    modifier: Modifier = Modifier,
    viewModel: MatchingViewModel = hiltViewModel(),
) {

    MatchingScreen(
        modifier = modifier,
    )
}

@Composable
private fun MatchingScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "매칭 관리",
            color = Color.White,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingScreenPreview() {
    SmashingAndroidTheme {
        MatchingScreen()
    }
}
