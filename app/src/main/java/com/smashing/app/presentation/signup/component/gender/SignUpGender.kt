package com.smashing.app.presentation.signup.component.gender

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_man_32
import com.smashing.app.R.drawable.ic_woman_32
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.presentation.signup.component.SignUpTitle
import com.smashing.app.presentation.signup.type.SelectedCardType


@Composable
fun SignUpGender (
    modifier: Modifier = Modifier,
) {

    var selectedCard by rememberSaveable { mutableStateOf<SelectedCardType?>(null)}

    Column (
        modifier = modifier,
    ){
        SignUpTitle(
            title = "성별을 선택해주세요",
            subTitle = "",
        )

        Row (
            modifier = Modifier
                .fillMaxWidth(),
        ){
            GenderCard(
                genderIcon = ic_man_32,
                genderText = "남성",
                onCardClick = { selectedCard = SelectedCardType.MALE },
                modifier = Modifier.weight(1f),
                isCardEnabled = selectedCard == SelectedCardType.MALE,

            )

            Spacer(modifier = Modifier.width(10.dp))

            GenderCard(
                genderIcon = ic_woman_32,
                genderText = "여성",
                onCardClick = { selectedCard = SelectedCardType.FEMALE },
                modifier = Modifier.weight(1f),
                isCardEnabled = selectedCard == SelectedCardType.FEMALE,
            )
        }
    }






}

@Preview(showBackground = true)
@Composable
private fun SignUpGenderPreview() {
    SmashingAndroidTheme {
        SignUpGender(
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
