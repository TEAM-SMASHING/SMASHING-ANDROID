package com.smashing.app.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smashing.app.R
import com.smashing.app.R.drawable.img_dummy_versus
import com.smashing.app.R.drawable.img_profile
import com.smashing.app.core.designsystem.component.button.SmashingBaseButton
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.style.SmashingBtnColor
import com.smashing.app.core.designsystem.style.getMatchButtonColor
import com.smashing.app.core.designsystem.style.getMatchButtonTitle
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.data.model.matching.AcceptedMatching

@Composable
fun CloseMatching(
    myNickname: String,
    myProfileId: String,
    onClick: (AcceptedMatching) -> Unit,
    modifier: Modifier = Modifier,
    matchedUser: AcceptedMatching? = null,
    navigateToSearch: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SmashingTheme.colors.bgSurface,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                horizontal = 16.dp,
            )
            .padding(
                top = 22.dp,
                bottom = 24.dp,
            ),
    ) {
        if (matchedUser != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = img_dummy_versus),
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            width = 100.dp,
                            height = 108.dp,
                        )
                        .align(Alignment.Center),
                )

                MatchedUserItem(
                    userId = myProfileId,
                    nickname = myNickname,
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                MatchedUserItem(
                    userId = matchedUser.userId,
                    nickname = matchedUser.nickname,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }

        } else {
            Text(
                text = stringResource(R.string.home_no_matching),
                style = SmashingTheme.typography.md.medium16,
                color = SmashingTheme.colors.txtTertiary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 30.dp,
                        bottom = 2.dp,
                    )
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        if (matchedUser != null) {
            SmashingBaseButton(
                text = matchedUser.resultStatus.getMatchButtonTitle(),
                textStyle = SmashingTheme.typography.md.medium16,
                onClick = { onClick(matchedUser) },
                buttonColor = matchedUser.resultStatus.getMatchButtonColor(),
                contentPadding = PaddingValues(
                    vertical = 9.dp,
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                isRippleEnabled = false,
            )
        } else {
            SmashingBaseButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp),
                text = "매칭 탐색하러 가기",
                textStyle = SmashingTheme.typography.md.medium16,
                onClick = navigateToSearch,
                buttonColor = SmashingBtnColor(
                    backgroundColor = SmashingTheme.colors.btnBgPrimary300,
                    textColor = SmashingTheme.colors.txtEmphasis,
                    disabledBackgroundColor = SmashingTheme.colors.btnBgPrimary300,
                    disabledTextColor = SmashingTheme.colors.txtEmphasis,
                ),
                contentPadding = PaddingValues(vertical = 9.dp),
                shape = RoundedCornerShape(8.dp),
                isRippleEnabled = false,
            )
        }
    }
}

@Composable
private fun MatchedUserItem(
    userId: String,
    nickname: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .padding(
                    horizontal = 28.dp,
                )
        ) {
            UrlImage(
                placeholderDrawable = img_profile,
                modifier = Modifier
                    .height(64.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape),
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = nickname,
            style = SmashingTheme.typography.sm.medium14,
            color = SmashingTheme.colors.txtMuted,
        )
    }
}
