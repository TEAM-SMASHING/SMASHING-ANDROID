package com.smashing.app.presentation.signup.component.sport

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_badminton
import com.smashing.app.R.drawable.ic_pingpong
import com.smashing.app.R.drawable.ic_tennis
import com.smashing.app.R.string.sign_up_sport_title
import com.smashing.app.R.string.sign_up_sport_subtitle
import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.designsystem.component.chip.SmashingChip
import com.smashing.app.core.designsystem.style.ChipStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.presentation.signup.component.SignUpTitle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@Composable
fun SignUpSport (
    items: ImmutableList<SportType>,
    modifier: Modifier = Modifier,
) {

    var selectedCard by rememberSaveable { mutableStateOf<SportType?>(null)}

    Column (
        modifier = modifier,
    ){
        SignUpTitle(
            title = stringResource(sign_up_sport_title),
            subTitle = stringResource(sign_up_sport_subtitle),
        )

        Row (
            modifier = Modifier
                .fillMaxWidth(),
        ){
            items.forEachIndexed { index, item ->
                SmashingChip(
                    text = item.sportName,
                    style = if(selectedCard == item) ChipStyle.ACTIVE else ChipStyle.INACTIVE,
                    onClick = { selectedCard = item },
                    icon = when(item) {
                        SportType.BADMINTON -> ImageVector.vectorResource(ic_badminton)
                        SportType.PING_PONG -> ImageVector.vectorResource(ic_pingpong)
                        SportType.TENNIS -> ImageVector.vectorResource(ic_tennis)
                    },
                )

                if(index != items.lastIndex){
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }






}

@Preview(showBackground = true)
@Composable
private fun SignUpSportPreview() {
    SmashingAndroidTheme {
        SignUpSport(
            items = persistentListOf(
                SportType.BADMINTON,
                SportType.PING_PONG,
                SportType.TENNIS,
            ),
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
