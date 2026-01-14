package com.smashing.app.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    navigateToNotice: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchDummyUsers()
    }
    HomeScreen(
        uiState = uiState,
        navigateToNotice = navigateToNotice,
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeContract.State,
    navigateToNotice: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
@Composable
private fun HomeTopBar(
    userRegion: String,
    userSport: SportType,
    userTier: TierType,
    onClickRegion: (String) -> Unit,
    onChangeRegion: () -> Unit,
    onClickSportChip: () -> Unit,
    onClickNotice: () -> Unit,
    modifier: Modifier = Modifier,
    isNotice: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RegionDropdown(
            selectedItem = userRegion,
            //items 현재는 지역이 1개라 userRegion만 넣었습니다.
            items = listOf(
                userRegion
            ).toImmutableList(),
            onClick = onClickRegion,
            onRegionChange = onChangeRegion,
            isDivide = true
        )

        Spacer(modifier = Modifier.weight(1f))

        SportsTierChip(
            sportType = userSport,
            tierType = userTier,
            onClick = onClickSportChip,
        )

        Spacer(modifier = Modifier.width(12.dp))

        if (!isNotice) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_bell),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .noRippleClickable(
                        onClick = onClickNotice,
                    )
            )
        } else {
            Icon(
                imageVector = ImageVector.vectorResource(ic_bell_notification),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .noRippleClickable(
                        onClick = onClickNotice,
                    )
            )
        }
    }
}

@Composable
private fun CloseMatching(
    matchedMyData: DummyMatchedUser,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    matchedUserData: DummyMatchedUser? = null,
    buttonState: String = "dummy",
) {
    //TODO 매칭 상대에서 받는 데이터 확인 후에 nickName + userId 묶는 데이터 타입 추가
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
        if (matchedUserData != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = img_dummy_versus),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center),
                )

                MatchedUserItem(
                    matchedUser= matchedMyData,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                )

                MatchedUserItem(
                    matchedUser = matchedUserData,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                )
            }

        } else {
            Text(
                text = "아직 확정된 매칭이 없어.\n지금 바로 매칭을 신청해보세요!",
                style = SmashingTheme.typography.md.medium16,
                color = SmashingTheme.colors.txtTertiary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 37.dp,
                        bottom = 23.dp,
                    )
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        //TODO buttonState 타입 수정 후 실제 사용시에 수정 예정
        SmashingButton(
            buttonStyle = ButtonStyle.PRIMARY,
            text = "결과 작성하기",
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "홈",
            color = Color.White,
            modifier = Modifier
                .clickable(onClick = navigateToNotice)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeContract.State(),
        navigateToNotice = {},
    )
}
