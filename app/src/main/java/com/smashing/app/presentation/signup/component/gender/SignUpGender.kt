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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_man_32
import com.smashing.app.R.drawable.ic_woman_32
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.presentation.signup.component.SignUpTitle
import com.smashing.app.R.string.sign_up_gender_title
import com.smashing.app.core.common.type.GenderType


@Composable
fun SignUpGender (
    selectedGender: GenderType?,
    onGenderSelected: (GenderType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier,
    ){
        SignUpTitle(
            title = stringResource(sign_up_gender_title),
            subTitle = "",
        )

        Row (
            modifier = Modifier
                .fillMaxWidth(),
        ){
            GenderCard(
                genderIcon = ic_man_32,
                genderText = GenderType.MALE.gender,
                onCardClick = { onGenderSelected(GenderType.MALE) },
                modifier = Modifier.weight(1f),
                isCardEnabled = selectedGender == GenderType.MALE,
            )

            Spacer(modifier = Modifier.width(10.dp))

            GenderCard(
                genderIcon = ic_woman_32,
                genderText = GenderType.FEMALE.gender,
                onCardClick = { onGenderSelected(GenderType.FEMALE)},
                modifier = Modifier.weight(1f),
                isCardEnabled = selectedGender == GenderType.FEMALE,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpGenderPreview() {
    SmashingAndroidTheme {
        SignUpGender(
            selectedGender = null,
            onGenderSelected = {},
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
