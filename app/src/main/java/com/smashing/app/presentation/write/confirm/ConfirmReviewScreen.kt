package com.smashing.app.presentation.write.confirm

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme

@Composable
fun ConfirmReviewRoute(
    navigateUp: () -> Unit,
    navigateToMatching: () -> Unit, // TODO 임시구현
    viewModel: ConfirmViewModel,
    modifier: Modifier = Modifier,
) {

}

@Composable
private fun ConfirmReviewScreen(
    modifier: Modifier =  Modifier,
) {

}

@Preview(showBackground = true)
@Composable
private fun ConfirmReviewScreenPreview() {
    SmashingAndroidTheme {
        ConfirmReviewScreen(

        )
    }
}
