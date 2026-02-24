package com.smashing.app.core.designsystem.component.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest
import coil3.request.crossfade

/**
 * URL을 통해 이미지를 비동기로 로드하여 표시하는 Composable입니다.
 *
 * Preview 모드에서는 placeholder 이미지를 표시하고,
 * 실제 실행 시에는 네트워크 이미지를 로드합니다.
 *
 * @param url 로드할 이미지의 URL
 * @param placeholderDrawable 이미지가 로드되기 전 혹은 img가 없을때 표시할 플레이스홀더 이미지의 리소스 ID
 * @param modifier Composable에 적용할 Modifier
 * @param contentScale 이미지 스케일링 방식 (기본: Fit)
 * @param contentDescription 접근성을 위한 이미지 설명
 */
@Composable
fun UrlImage(
    modifier: Modifier = Modifier,
    url: String = "",
    @DrawableRes placeholderDrawable: Int? = null,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
) {
    if (LocalInspectionMode.current) {
        placeholderDrawable?.let { drawableRes ->
            Image(
                painter = painterResource(drawableRes),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = modifier,
            )
        }
        return
    }

    val fallbackContent: @Composable () -> Unit = {
        placeholderDrawable?.let { drawableRes ->
            Image(
                painter = painterResource(drawableRes),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier,
            )
        }
    }

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(url.ifBlank { null })
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        success = { SubcomposeAsyncImageContent() },
        error = { fallbackContent() },
    )
}

@Preview
@Composable
private fun UrlImagePreview() {
    UrlImage(
        url = "",
        modifier = Modifier.size(100.dp),
    )
}
