package com.smashing.app.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R
import com.smashing.app.R.drawable.ic_bell
import com.smashing.app.R.drawable.ic_bell_notification
import com.smashing.app.R.drawable.img_dummy_versus
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.card.MatchingCard
import com.smashing.app.core.designsystem.component.dropdown.RegionDropdown
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.component.ranking.SmashingRankingItem
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.core.util.ProfileImageProvider
import com.smashing.app.data.model.profile.ActiveUserProfile
import com.smashing.app.data.model.rank.TopUserInfo
import com.smashing.app.presentation.home.component.HomeDropdown
import com.smashing.app.presentation.home.component.SportsTierChip
import com.smashing.app.presentation.home.type.DummyMatchedUser
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeRoute(
    navigateToNotice: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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

    var isDropdownExpanded by remember { mutableStateOf(false) }
    var topBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    // 스포츠 리스트 가져오기 (실제로는 ViewModel이나 다른 곳에서 가져와야 함)
    val sportList = remember {
        listOf(
            SportType.TENNIS,
            SportType.PING_PONG,
            SportType.BADMINTON,
        ).toImmutableList()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (isDropdownExpanded) SmashingTheme.colors.bgSurface else Color.Transparent,
                )
                .onGloballyPositioned { coordinates ->
                    topBarHeight = with(density) {
                        coordinates.size.height.toDp()
                    }
                }
                .statusBarsPadding()
        ) {
            HomeTopBar(
                userRegion = uiState.activeUserProfile.region,
                userSport = uiState.activeUserProfile.sportType,
                userTier = uiState.activeUserProfile.tierType,
                onClickRegion = {},
                onChangeRegion = {},
                onClickSportChip = { isDropdownExpanded = !isDropdownExpanded },
                onClickNotice = navigateToNotice,
                isNotice = uiState.isNotice,
            )
        }

        HomeDropdown(
            isExpanded = isDropdownExpanded,
            activeSport = uiState.activeUserProfile.sportType,
            sportList = sportList,
            tierType = uiState.activeUserProfile.tierType,
            minLp = uiState.activeUserProfile.minLp,
            maxLp = uiState.activeUserProfile.maxLp,
            winCount = uiState.activeUserProfile.wins,
            loseCount = uiState.activeUserProfile.losses,
            onSportChipClick = {
                // 스포츠 변경 로직 (필요시 추가)
                isDropdownExpanded = false
            },
            onSportAddClick = {
                // 스포츠 추가 로직 (필요시 추가)
                isDropdownExpanded = false
            },
            onTierClick = {
                isDropdownExpanded = false
            },
            onDismiss = {
                isDropdownExpanded = false
            },
            triggerHeight = topBarHeight,
        )

        LazyColumn(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                )
                .padding(top = 12.dp),
            contentPadding = PaddingValues(
                bottom = 22.dp,
            )
        ) {
            item {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Column {
                            Text(
                                text = "${uiState.activeUserProfile.nickname}님,",
                                style = SmashingTheme.typography.xxl.semibold24,
                                color = SmashingTheme.colors.txtPrimary,
                            )
                            Text(
                                text = stringResource(R.string.home_clos_matching_txt),
                                style = SmashingTheme.typography.xl.semibold20,
                                color = SmashingTheme.colors.txtPrimary,
                            )
                        }

                        Text(
                            text = stringResource(R.string.home_all_text),
                            style = SmashingTheme.typography.sm.medium14,
                            color = SmashingTheme.colors.txtTertiary,
                            modifier = Modifier
                                .noRippleClickable(
                                    onClick = {}
                                ),
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    //TODO 아래 유저 ID에 profileId를 임시로 넣었어요. 받는 값에 유저ID가 없어...
                    CloseMatching(
                        matchedMyData = DummyMatchedUser(
                            userId = uiState.activeUserProfile.profileId,
                            nickname = uiState.activeUserProfile.nickname,
                        ),
                        matchedUserData = uiState.matchedUser,
                        onClick = {},
                    )
                }
            }
            //TODO 매칭

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "${uiState.activeUserProfile.nickname}님을 위한 추천",
                            style = SmashingTheme.typography.lg.semibold18,
                            color = SmashingTheme.colors.txtPrimary,
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_info),
                            contentDescription = null,
                            tint = SmashingTheme.colors.iconTertiary,
                            modifier = Modifier
                                .noRippleClickable(
                                    //TODO 알림 창 확인 후 구현
                                    onClick = {}
                                ),
                        )
                    }
                    if (uiState.matchingCardList.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            items(
                                items = uiState.matchingCardList,
                                key = { it.userId }
                            ) { cardState ->
                                MatchingCard(
                                    cardState = cardState,
                                )
                            }
                        }
                    } else {
                        Text(
                            text = stringResource(R.string.home_no_user),
                            style = SmashingTheme.typography.md.medium16,
                            color = SmashingTheme.colors.txtTertiary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = SmashingTheme.colors.bgSurface,
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .padding(
                                    vertical = 31.dp
                                )
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = stringResource(R.string.home_region_ranker),
                            style = SmashingTheme.typography.lg.semibold18,
                            color = SmashingTheme.colors.txtPrimary,
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = stringResource(R.string.home_all_text),
                            style = SmashingTheme.typography.sm.medium14,
                            color = SmashingTheme.colors.txtTertiary,
                            modifier = Modifier
                                .noRippleClickable(
                                    onClick = {}
                                )
                        )
                    }
                    uiState.topRankerList.forEach { ranker ->
                        SmashingRankingItem(
                            rank = ranker.rank,
                            nickname = ranker.nickname,
                            tier = ranker.tier,
                            lp = ranker.lp,
                            userId = ranker.userId,
                            onClick = {},
                        )

                    }
                }
            }
        }
    }
}

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
                    matchedUser = matchedMyData,
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
                text = stringResource(R.string.home_no_matching),
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

@Composable
private fun MatchedUserItem(
    matchedUser: DummyMatchedUser,
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
                url = ProfileImageProvider.getTempUrl(matchedUser.userId),
                modifier = Modifier
                    .height(64.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape),
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = matchedUser.nickname,
            style = SmashingTheme.typography.sm.medium14,
            color = SmashingTheme.colors.txtMuted,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeContract.State(
            activeUserProfile = ActiveUserProfile(
                nickname = "Test",
                region = "서울",
                profileId = "0USP111222333",
                sportType = SportType.TENNIS,
                tierType = TierType.GOLD_1,
                lp = 123,
                minLp = 100,
                maxLp = 500,
                wins = 10,
                losses = 7,
            ),
            topRankerList = listOf(
                TopUserInfo(
                    userId = "user1",
                    nickname = "1위 유저",
                    rank = 1,
                    tier = TierType.CHALLENGER,
                    lp = 2500,
                ),
                TopUserInfo(
                    userId = "user2",
                    nickname = "열글자테스트중입니다",
                    rank = 2,
                    tier = TierType.CHALLENGER,
                    lp = 2450,
                ),
                TopUserInfo(
                    userId = "user3",
                    nickname = "1위 유저",
                    rank = 3,
                    tier = TierType.CHALLENGER,
                    lp = 2400,
                ),
                TopUserInfo(
                    userId = "user4",
                    nickname = "프로게이머",
                    rank = 4,
                    tier = TierType.DIAMOND_1,
                    lp = 2350,
                ),
                TopUserInfo(
                    userId = "user5",
                    nickname = "랭커킹커",
                    rank = 5,
                    tier = TierType.DIAMOND_1,
                    lp = 2300,
                ),
                TopUserInfo(
                    userId = "user6",
                    nickname = "승리만추구",
                    rank = 6,
                    tier = TierType.DIAMOND_2,
                    lp = 2250,
                ),
                TopUserInfo(
                    userId = "user7",
                    nickname = "플래티넘마스터",
                    rank = 7,
                    tier = TierType.DIAMOND_2,
                    lp = 2200,
                ),
                TopUserInfo(
                    userId = "user8",
                    nickname = "골드라이더",
                    rank = 8,
                    tier = TierType.DIAMOND_3,
                    lp = 2150,
                ),
                TopUserInfo(
                    userId = "user9",
                    nickname = "실버도전자",
                    rank = 9,
                    tier = TierType.PLATINUM_1,
                    lp = 2100,
                ),
                TopUserInfo(
                    userId = "user10",
                    nickname = "브론즈탈출",
                    rank = 10,
                    tier = TierType.PLATINUM_2,
                    lp = 2050,
                ),
            ).toImmutableList(),
            matchingCardList = listOf(
                MatchingCardState.Search(
                    userId = "match1",
                    nickname = "탁구의신",
                    genderType = GenderType.MALE,
                    tierType = TierType.DIAMOND_1,
                    onProfileClick = {},
                    winCount = 254,
                    loseCount = 38,
                    reviewCount = 32,
                ),
                MatchingCardState.Search(
                    userId = "match2",
                    nickname = "테니스마스터",
                    genderType = GenderType.FEMALE,
                    tierType = TierType.PLATINUM_2,
                    onProfileClick = {},
                    winCount = 180,
                    loseCount = 45,
                    reviewCount = 28,
                ),
                MatchingCardState.Search(
                    userId = "match3",
                    nickname = "배드민턴킹",
                    genderType = GenderType.MALE,
                    tierType = TierType.GOLD_1,
                    onProfileClick = {},
                    winCount = 150,
                    loseCount = 60,
                    reviewCount = 25,
                ),
            ).toImmutableList(),
            matchedUser = DummyMatchedUser(
                userId = "matchedUser1",
                nickname = "더미하는김에긴닉네임",
            ),
            loadState = HomeUiState.Success,
            isNotice = true,
        ),
        navigateToNotice = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenEmptyValuePreview() {
    HomeScreen(
        uiState = HomeContract.State(
            activeUserProfile = ActiveUserProfile(
                nickname = "Test",
                region = "서울",
                profileId = "0USP111222333",
                sportType = SportType.TENNIS,
                tierType = TierType.GOLD_1,
                lp = 123,
                minLp = 100,
                maxLp = 500,
                wins = 10,
                losses = 7,
            ),
            topRankerList = listOf(
                TopUserInfo(
                    userId = "user1",
                    nickname = "1위 유저",
                    rank = 1,
                    tier = TierType.CHALLENGER,
                    lp = 2500,
                ),
            ).toImmutableList(),
            matchingCardList = listOf<MatchingCardState.Search>().toImmutableList(),
            loadState = HomeUiState.Success,
            isNotice = false,
        ),
        navigateToNotice = {},
    )
}
