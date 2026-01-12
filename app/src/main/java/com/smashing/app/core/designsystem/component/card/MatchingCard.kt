package com.smashing.app.core.designsystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R
import com.smashing.app.R.drawable.ic_close_sm
import com.smashing.app.R.drawable.ic_link
import com.smashing.app.R.string.accept
import com.smashing.app.core.common.type.GenderType
import com.smashing.app.core.common.type.TierType
import com.smashing.app.core.designsystem.component.button.SmashingBaseButton
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.core.designsystem.state.MatchingCardState.Confirm
import com.smashing.app.core.designsystem.state.MatchingCardState.Receive
import com.smashing.app.core.designsystem.state.MatchingCardState.Search
import com.smashing.app.core.designsystem.state.MatchingCardState.Send
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable

/**
 * 매칭 카드 컴포넌트
 *
 * 카드의 전체 UI 구성과 노출되는 액션은 [MatchingCardState]의 타입에 의해 결정됩니다.
 * UI 레이어에서는 상태 타입만을 기준으로 분기하며,
 * 각 상태가 허용하는 이벤트만 안전하게 호출할 수 있습니다.
 *
 * @param cardState
 * 카드의 표시 데이터와 사용자 액션을 포함하는 상태 객체
 *
 * - 공통: 프로필 영역이 항상 노출되며, 클릭 시 [MatchingCardState.onProfileClick]이 호출
 *
 * - [MatchingCardState.Closable]: 우측 상단 닫기(X) 버튼이 노출
 *
 * - [Receive]: 하단에 "건너뛰기", "수락" 버튼이 노출
 *
 * - [Confirm]: 카카오 링크 영역과 "결과 작성" 버튼이 노출
 *
 * - 그 외 상태([Search], [Send]): 하단 액션 영역이 노출x
 *
 */
@Composable
fun MatchingCard(
    cardState: MatchingCardState,
    modifier: Modifier = Modifier,
) {
    val topPadding = if (cardState is MatchingCardState.Closable) 8.dp else 16.dp

    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .background(
                color = SmashingTheme.colors.bgSurface,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                top = topPadding,
                bottom = 16.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        (cardState as? MatchingCardState.Closable)?.let { closable ->
            Icon(
                imageVector = ImageVector.vectorResource(id = ic_close_sm),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .noRippleClickable(onClick = closable.onCloseClick)
                    .padding(end = 8.dp)
                    .align(Alignment.End),
            )
        }

        MatchingCardContent(
            cardState = cardState,
        )

        when (cardState) {
            is Receive ->
                SendButtons(
                    onSkipClick = cardState.onSkipClick,
                    onAcceptClick = cardState.onAcceptClick,
                    modifier = Modifier.padding(top = 8.dp),
                )

            is Confirm -> {
                Row(
                    modifier = Modifier
                        .noRippleClickable(onClick = cardState.onKakaoLinkClick)
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement
                        .spacedBy(
                            space = 4.dp,
                            alignment = Alignment.CenterHorizontally,
                        ),
                ) {
                    Text(
                        text = stringResource(R.string.kakao_link),
                        color = SmashingTheme.colors.txtKakaoLinkGray,
                        style = SmashingTheme.typography.xs.regular12,
                        textDecoration = TextDecoration.Underline,
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(id = ic_link),
                        contentDescription = null,
                        tint = SmashingTheme.colors.iconPrimary,
                    )
                }

                SmashingBaseButton(
                    text = stringResource(R.string.matching_write),
                    textStyle = SmashingTheme.typography.sm.medium14,
                    onClick = cardState.onConfirmClick,
                    buttonColor = ButtonStyle.SECONDARY.getButtonColor(),
                    contentPadding = PaddingValues(
                        vertical = 3.dp,
                        horizontal = 20.dp,
                    ),
                    shape = RoundedCornerShape(4.dp),
                )
            }

            else -> Unit
        }
    }
}

@Composable
private fun SendButtons(
    onSkipClick: () -> Unit,
    onAcceptClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement
            .spacedBy(
                space = 20.dp,
                alignment = Alignment.CenterHorizontally,
            ),
    ) {
        Text(
            text = stringResource(R.string.skip),
            style = SmashingTheme.typography.xs.regular12,
            color = SmashingTheme.colors.txtSecondary,
            modifier = Modifier.noRippleClickable(onClick = onSkipClick),
        )

        SmashingBaseButton(
            text = stringResource(accept),
            textStyle = SmashingTheme.typography.sm.medium14,
            onClick = onAcceptClick,
            buttonColor = ButtonStyle.SECONDARY.getButtonColor(),
            contentPadding = PaddingValues(
                vertical = 3.dp,
                horizontal = 15.dp,
            ),
            shape = RoundedCornerShape(4.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingCardPreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                MatchingCard(
                    cardState = Send(
                        userId = "userId",
                        nickname = "하나둘셋넷다여칠팔",
                        genderType = GenderType.MALE,
                        tierType = TierType.BRONZE_2,
                        winCount = 254,
                        loseCount = 38,
                        reviewCount = 32,
                        onCloseClick = {},
                        onProfileClick = {},
                    ),
                )

                MatchingCard(
                    cardState = Receive(
                        userId = "userId",
                        nickname = "하나둘셋넷다여칠팔",
                        genderType = GenderType.MALE,
                        tierType = TierType.BRONZE_2,
                        winCount = 254,
                        loseCount = 38,
                        reviewCount = 32,
                        onProfileClick = {},
                        onSkipClick = {},
                        onAcceptClick = {},
                    ),
                )

                MatchingCard(
                    cardState = Confirm(
                        userId = "userId",
                        nickname = "하나둘셋넷다여칠팔",
                        genderType = GenderType.MALE,
                        tierType = TierType.BRONZE_2,
                        onProfileClick = {},
                        onKakaoLinkClick = {},
                        onCloseClick = {},
                        onConfirmClick = {},
                    ),
                )

                MatchingCard(
                    cardState = Search(
                        userId = "userId",
                        nickname = "하나둘셋넷다여칠팔",
                        genderType = GenderType.MALE,
                        tierType = TierType.BRONZE_2,
                        winCount = 254,
                        loseCount = 38,
                        reviewCount = 32,
                        onProfileClick = {},
                    )
                )
            }
        }
    }
}
