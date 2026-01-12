package com.smashing.app.core.designsystem.component.progressbar

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.string.progress_label
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors

/**
 * 프로그래스바 공통 컴포넌트입니다.
 *
 * @param progress  프로그래스바 진행률 (최소 0.0, 최대 1.0)
 */

private const val durationMillis = 500

@Composable
fun SmashingProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = stringResource(progress_label),
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(
                color = colors.stateProgressTrack,
                shape = RoundedCornerShape(12.dp),
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .height(8.dp)
                .background(
                    color = colors.stateProgressFill,
                    shape = RoundedCornerShape(12.dp),
                ),
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SmashingProgressBarPreview() {
    SmashingAndroidTheme {
        var currentStep by rememberSaveable{ mutableIntStateOf(0) }
        val progress = when (currentStep) {
            0 -> 0f
            1 -> 0.16f
            2 -> 0.32f
            3 -> 0.48f
            4 -> 0.60f
            5 -> 0.76f
            else -> 1f
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(
                onClick = { currentStep = currentStep + 1 }
            ) {
                Text(
                    text = "진행"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            
            SmashingProgressBar(
                progress = progress,
            )

            Spacer(modifier = Modifier.height(10.dp))

            SmashingProgressBar(
                progress = 0.6f,
            )

            Spacer(modifier = Modifier.height(10.dp))

            SmashingProgressBar(
                progress = 0.3f,
            )
        }

    }
}

