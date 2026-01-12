package com.smashing.app.presentation.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.R.string.kakao_login
import com.smashing.app.R.drawable.ic_kakao_logo
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable


@Composable
fun KakaoLoginButton(
    onLoginBtnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0XFFFEE500),
                shape = RoundedCornerShape(10.dp),
            )
            .noRippleClickable(
                onClick = onLoginBtnClick,
            )
            .padding(vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_kakao_logo),
            contentDescription = null,
            modifier = Modifier
                .size(18.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(kakao_login),
            color = colors.txtPrimaryReverse,
            style = typography.md.medium16,
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun KakaoLoginButtonPreview() {
    KakaoLoginButton(
        onLoginBtnClick = {}
    )
}
