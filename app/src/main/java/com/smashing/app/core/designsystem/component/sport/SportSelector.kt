package com.smashing.app.core.designsystem.component.sport

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_badminton
import com.smashing.app.R.drawable.ic_pingpong
import com.smashing.app.R.drawable.ic_tennis
import com.smashing.app.R.string.sign_up_sport_subtitle
import com.smashing.app.R.string.sign_up_sport_title
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.mapper.icon
import com.smashing.app.core.designsystem.style.ChipStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.data.type.SportType
import com.smashing.app.presentation.signup.component.SignUpTitle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@Composable
fun SportSelector(
    items: ImmutableList<SportType>,
    selectedSport: SportType?,
    onSportSelected: (SportType) -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(sign_up_sport_title),
    subTitle: String = stringResource(sign_up_sport_subtitle),
    isSubTitle: Boolean= true,
) {
    Column(
        modifier = modifier,
    ) {

        SignUpTitle(
            title = title,
            subTitle = subTitle,
            isSubTitle = isSubTitle,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            items.forEachIndexed { index, item ->
                SmashingChip(
                    text = item.sportName,
                    style = if (selectedSport == item) ChipStyle.ACTIVE else ChipStyle.INACTIVE,
                    onClick = { onSportSelected(item) },
                    icon = ImageVector.vectorResource(item.icon()),
                    )

                if (index != items.lastIndex) {
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SportSelectorPreview() {
    SmashingAndroidTheme {
        SportSelector(
            items = persistentListOf(
                SportType.BADMINTON,
                SportType.PING_PONG,
                SportType.TENNIS,
            ),
            selectedSport = null,
            onSportSelected = {},
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
