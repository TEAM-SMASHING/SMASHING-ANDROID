package com.smashing.app.presentation.signup.component.gender

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable

@Composable
fun GenderCard (
    @DrawableRes genderIcon: Int,
    genderText: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCardEnabled: Boolean = false,
){
    Column(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                color = if(isCardEnabled) colors.bgCanvasReverse else Color.Transparent,
                shape = RoundedCornerShape(12.dp),
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(12.dp),
                color = colors.borderSecondary,
            )
            .noRippleClickable(
                onClick = onCardClick,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(genderIcon),
            contentDescription = null,
            tint = if(isCardEnabled) colors.iconPrimaryReverse else colors.iconPrimary,
        )

        Text(
            text = genderText,
            color = if(isCardEnabled) colors.txtPrimaryReverse else colors.txtPrimary,
            style = typography.md.medium16,
        )
    }
}
