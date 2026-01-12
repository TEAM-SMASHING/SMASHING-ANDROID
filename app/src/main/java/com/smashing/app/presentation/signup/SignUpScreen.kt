package com.smashing.app.presentation.signup

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
import com.smashing.app.core.extension.noRippleClickable

@Composable
fun SignUpRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {

    SignUpScreen(
        onSignupClick = {
            viewModel.postSignUp(
                onSignupSuccess = navigateToHome,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun SignUpScreen(
    onSignupClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    // TODO: 추후 수정 예정
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "SignUp",
            modifier = Modifier
                .noRippleClickable(
                    onClick = { onSignupClick },
                ),
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen(
        navigateToHome = {}
    )
}
