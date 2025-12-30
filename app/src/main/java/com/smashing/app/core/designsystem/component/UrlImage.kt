package com.smashing.app.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.smashing.app.R.drawable.ic_fake_red

/**
 * URL을 통해 이미지를 비동기로 로드하여 표시하는 Composable입니다.
 *
 * Preview 모드에서는 placeholder 이미지를 표시하고,
 * 실제 실행 시에는 네트워크 이미지를 로드합니다.
 *
 * @param url 로드할 이미지의 URL
 * @param modifier Composable에 적용할 Modifier
 * @param contentScale 이미지 스케일링 방식 (기본: Fit)
 * @param contentDescription 접근성을 위한 이미지 설명
 */
@Composable
fun UrlImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
) {
    if (LocalInspectionMode.current) {
        Image(
            imageVector = ImageVector.vectorResource(ic_fake_red),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
        )
    } else {
        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
        )
    }
}

@Preview
@Composable
fun UrlImagePreview() {
    UrlImage(
        url = "",
        modifier = Modifier.size(100.dp),
    )
}
