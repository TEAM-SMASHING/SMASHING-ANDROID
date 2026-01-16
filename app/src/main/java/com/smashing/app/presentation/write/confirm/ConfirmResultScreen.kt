package com.smashing.app.presentation.write.confirm

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme

@Composable
fun ConfirmResultRoute(
    navigateUp: () -> Unit,
    navigateToConfirmReview: () -> Unit,
    viewModel: ConfirmViewModel,
    modifier: Modifier = Modifier,
) {

}

@Composable
private fun ConfirmResultScreen(
    modifier: Modifier = Modifier,
) {

}

@Preview(showBackground = true)
@Composable
private fun ConfirmResultScreenPreview() {
    SmashingAndroidTheme {
        ConfirmResultScreen(

        )
    }
}
