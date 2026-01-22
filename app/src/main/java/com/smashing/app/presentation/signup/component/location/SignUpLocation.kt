package com.smashing.app.presentation.signup.component.location

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.presentation.signup.component.SignUpTitle
import com.smashing.app.R.string.sign_up_location_title
import com.smashing.app.R.string.sign_up_location_subtitle


@Composable
fun SignUpLocation(
    addressText: String ,
    isAddressExist: Boolean,
    onAddressClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        SignUpTitle(
            title = stringResource(sign_up_location_title),
            subTitle = stringResource(sign_up_location_subtitle),
        )

        Text(
            text = addressText,
            color = if (isAddressExist) colors.txtPrimary else colors.txtDisabled,
            style = typography.sm.medium14,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(8.dp),
                )
                .border(
                    width = 1.dp,
                    color = colors.borderSecondary,
                    shape = RoundedCornerShape(8.dp),
                )
                .noRippleClickable(
                    onClick = onAddressClick
                )
                .padding(vertical = 13.dp)
                .padding(start = 16.dp),
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SignUpLocationPreview() {
    SmashingAndroidTheme {
        SignUpLocation(
            addressText = "도로명 주소를 검색해주세요",
            isAddressExist = false,
            onAddressClick = {},
            modifier = Modifier.background(color = colors.bgCanvas)
        )
    }
}
