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
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.MatchingCardStyle
import com.smashing.app.core.designsystem.style.MatchingCardStyle.CONFIRM
import com.smashing.app.core.designsystem.style.MatchingCardStyle.RECEIVE
import com.smashing.app.core.designsystem.style.MatchingCardStyle.SEARCH
import com.smashing.app.core.designsystem.style.MatchingCardStyle.SEND
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable

/**
 * 사용자의 프로필 정보와 매칭 상태에 따른 액션을 표시하는 카드 컴포넌트입니다.
 * * [cardType]에 따라 우측 상단 닫기 버튼 노출 여부와 하단 액션 버튼(수락, 건너뛰기, 링크 등)의 구성이 달라집니다.
 *
 * @param cardType 매칭 카드의 상태 스타일 ([MatchingCardStyle.SEARCH], [SEND], [RECEIVE], [CONFIRM])
 * @param userId 사용자의 고유 식별자
 * @param nickname 표시될 사용자의 닉네임
 * @param genderType 사용자의 성별 정보 ([GenderType])
 * @param tierType 사용자의 게임 티어 정보 ([TierType])
 * @param winCount 총 승리 횟수
 * @param loseCount 총 패배 횟수
 * @param reviewCount 작성된 리뷰 수
 * @param onProfileClick 프로필 카드 클릭
 * @param onCloseClick 우측 상단 닫기(X) 버튼 클릭
 * @param onSkipClick 매칭 요청 건너뛰기 클릭
 * @param onAcceptClick 매칭 요청 수락 클릭
 * @param onKakaoLinkClick 카카오톡 링크 클릭
 * @param onConfirmClick 매칭 확정 결과 작성 클릭
 */
@Composable
fun MatchingCard(
    cardType: MatchingCardStyle,
    userId: String,
    nickname: String,
    genderType: GenderType,
    tierType: TierType,
    winCount: Long,
    loseCount: Long,
    reviewCount: Long,
    modifier: Modifier = Modifier,
    onProfileClick: (() -> Unit)? = null,
    onCloseClick: (() -> Unit)? = null,
    onSkipClick: (() -> Unit)? = null,
    onAcceptClick: (() -> Unit)? = null,
    onKakaoLinkClick: (() -> Unit)? = null,
    onConfirmClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .background(
                color = SmashingTheme.colors.bgSurface,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                top = if (cardType.isCancelAble) 8.dp else 16.dp,
                bottom = 16.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (cardType.isCancelAble) {
            Icon(
                imageVector = ImageVector.vectorResource(id = ic_close_sm),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .noRippleClickable(onClick = { onCloseClick?.invoke() })
                    .padding(
                        end = 8.dp,
                    )
                    .align(alignment = Alignment.End),
            )
        }

        MatchingCardContent(
            cardType = cardType,
            userId = userId,
            nickname = nickname,
            genderType = genderType,
            tierType = tierType,
            winCount = winCount,
            loseCount = loseCount,
            reviewCount = reviewCount,
            onProfileClick = { onProfileClick?.invoke() },
        )

        when (cardType) {
            RECEIVE ->
                SendButtons(
                    onSkipClick = { onSkipClick?.invoke() },
                    onAcceptClick = { onAcceptClick?.invoke() },
                    modifier = Modifier.padding(top = 8.dp),
                )

            CONFIRM -> {
                Row(
                    modifier = Modifier
                        .noRippleClickable(onClick = { onKakaoLinkClick?.invoke() })
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
                    onClick = { onConfirmClick?.invoke() },
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
                    userId = "userId",
                    nickname = "하나둘셋넷다여칠팔구",
                    genderType = GenderType.MALE,
                    tierType = TierType.BRONZE_1,
                    winCount = 2545555,
                    loseCount = 38,
                    reviewCount = 32,
                    cardType = SEARCH,
                )

                MatchingCard(
                    userId = "userId",
                    nickname = "하나둘셋넷다여칠팔구",
                    genderType = GenderType.MALE,
                    tierType = TierType.BRONZE_1,
                    winCount = 254,
                    loseCount = 38,
                    reviewCount = 32,
                    cardType = SEND,
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                MatchingCard(
                    userId = "userId",
                    nickname = "하나둘셋넷다여칠팔구",
                    genderType = GenderType.MALE,
                    tierType = TierType.BRONZE_1,
                    winCount = 254,
                    loseCount = 38,
                    reviewCount = 32,
                    cardType = RECEIVE,
                )

                MatchingCard(
                    userId = "userId",
                    nickname = "하나둘셋넷다여칠팔구",
                    genderType = GenderType.MALE,
                    tierType = TierType.BRONZE_1,
                    winCount = 254,
                    loseCount = 38,
                    reviewCount = 333332,
                    cardType = CONFIRM,
                )
            }
        }
    }
}
