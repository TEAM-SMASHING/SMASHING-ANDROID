package com.smashing.app.presentation.submit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R
import com.smashing.app.core.designsystem.theme.SmashingTheme

@Composable
fun SubmitRoute(
    modifier: Modifier = Modifier,
    viewModel: SubmitViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SubmitScreen(
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
private fun SubmitScreen(
    uiState: SubmitContract.State,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.submit_title),
            color = SmashingTheme.colors.txtPrimary,
            style = SmashingTheme.typography.xl.semibold20,
        )

        Text(
            text = stringResource(R.string.submit_description),
            color = SmashingTheme.colors.txtTertiary,
            style = SmashingTheme.typography.sm.medium14,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SubmitScreenPreview() {
    SubmitScreen(
        uiState = SubmitContract.State(),
    )
}
