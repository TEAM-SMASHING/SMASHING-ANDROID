package com.smashing.app.core.designsystem.component.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme

@Composable
fun SmashingToast(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SmashingTheme.colors.bgCanvasReverse,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                vertical = 10.dp,
                horizontal = 16.dp,
            ),
        textAlign = TextAlign.Start,
        style = SmashingTheme.typography.xs.medium12,
        color = SmashingTheme.colors.txtPrimaryReverse,
    )
}

@Preview(showBackground = true)
@Composable
private fun SmashingToastPreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SmashingToast(
                text = "매칭을 수락했어요! 매칭 확정 탭에서 확인해주세요.",
                modifier = Modifier
                    .padding(bottom = 46.dp)
                    .padding(horizontal = 16.dp),
            )
        }
    }
}
