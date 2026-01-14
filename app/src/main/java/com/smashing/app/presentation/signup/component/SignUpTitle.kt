package com.smashing.app.presentation.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography

@Composable
fun SignUpTitle (
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier,
    ){
        Text(
            text = title,
            color = colors.txtPrimary,
            style = typography.xl.semibold20,
        )
        Text(
            text = subTitle,
            color = colors.txtTertiary,
            style = typography.sm.medium14,
        )

        Spacer(modifier = Modifier.height(28.dp))
    }
}
