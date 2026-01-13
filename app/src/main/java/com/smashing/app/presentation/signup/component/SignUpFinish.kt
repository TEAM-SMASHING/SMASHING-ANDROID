package com.smashing.app.presentation.signup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.string.sign_up_finish_title
import com.smashing.app.R.string.sign_up_finish_subtitle
import com.smashing.app.R.drawable.ic_check_onboarding
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography


@Composable
fun SignUpFinish(
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Icon(
            painter = painterResource(ic_check_onboarding),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(sign_up_finish_title),
            color = colors.txtPrimary,
            style = typography.hero.bold28,
        )

        Text(
            text = stringResource(sign_up_finish_subtitle),
            color = colors.txtTertiary,
            style = typography.md.medium16,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpFinishPreview() {
    SmashingAndroidTheme {
        SignUpFinish()
    }
}

