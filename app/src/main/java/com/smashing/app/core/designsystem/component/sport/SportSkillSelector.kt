package com.smashing.app.core.designsystem.component.sport

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_radio_fill
import com.smashing.app.R.drawable.ic_radio_empty
import com.smashing.app.R.string.sign_up_skill_title
import com.smashing.app.R.string.sign_up_skill_subtitle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.core.common.type.SkillType
import com.smashing.app.presentation.signup.component.SignUpTitle
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SportSkillSelector(
    selectedSkill: SkillType?,
    onSkillSelected: (SkillType) -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(sign_up_skill_title),
    subTitle: String = stringResource(sign_up_skill_subtitle),
) {
    val radioList = SkillType.entries.toImmutableList()

    Column(
        modifier = modifier.selectableGroup(),
    ) {
        SignUpTitle(
            title = title,
            subTitle = subTitle,
        )
        radioList.forEach { item ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .noRippleClickable(
                        onClick = { onSkillSelected(item) }
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        if (selectedSkill == item) ic_radio_fill else ic_radio_empty
                    ),
                    contentDescription = null,
                    tint = colors.iconPrimary,
                )

                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = item.skillText,
                    color = colors.txtSecondary,
                    style = typography.md.medium16,
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SportSkillSelectorPreview() {
    SmashingAndroidTheme {
        SportSkillSelector(
            selectedSkill = null,
            onSkillSelected = {},
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
