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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.smashing.app.core.designsystem.component.button.SmashingBaseButton
import com.smashing.app.core.designsystem.component.card.MatchingCard
import com.smashing.app.core.designsystem.component.dropdown.RegionDropdown
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.component.ranking.SmashingRankingItem
import com.smashing.app.core.designsystem.component.toast.LocalToastTrigger
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.core.designsystem.style.SmashingBtnColor
import com.smashing.app.core.designsystem.style.TierInfoStyle
import com.smashing.app.core.designsystem.style.getMatchButtonColor
import com.smashing.app.core.designsystem.style.getMatchButtonTitle
import com.smashing.app.core.designsystem.style.toTierInfoStyle
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.core.util.ProfileImageProvider
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.my.ActiveUserProfile
import com.smashing.app.data.model.rank.UserRank
import com.smashing.app.data.model.search.SearchMainItemModel
import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import com.smashing.app.presentation.home.component.HomeDropdown
import com.smashing.app.presentation.home.component.SportsTierChip
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeRoute(
    navigateToNotice: (String) -> Unit,
    navigateToRegionChange: () -> Unit,
    navigateToTierInfo: (TierInfoStyle, SportType) -> Unit,
    navigateToRanking: () -> Unit,
    navigateToMatchingAccepted: () -> Unit,
    navigateToUserProfile: (String) -> Unit,
    navigateToSportAdd: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToSubmit: (
        gameId: String,
        opponentUserId: String,
        opponentNickname: String,
        isFirstAttempt: Boolean,
    ) -> Unit,
    navigateToConfirm: (
        submissionId: String,
        gameId: String,
        isFirstAttempt: Boolean,
    ) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val recommendedUserListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.fetchMyTierProfile()
        viewModel.fetchRegionRankerList()
        viewModel.fetchRecommendedUserList()
        viewModel.fetchMatchedUser()
    }


    LaunchedEffect(uiState.recommendedUserList) {
        recommendedUserListState.scrollToItem(0)
    }

    // TODO 토스트 예시
    val show = LocalToastTrigger.current
    show.invoke("토스트 테스트입니다.")
    show.invoke("토스트 테스트입니다.")

    HomeScreen(
        uiState = uiState,
        navigateToNotice = {
            uiState.activeUserProfile?.profileId?.let(navigateToNotice)
        },
        navigateToRegionChange = navigateToRegionChange,
        navigateToTierInfo = {
            navigateToTierInfo(
                uiState.activeUserProfile?.tierType?.toTierInfoStyle() ?: TierInfoStyle.IRON,
                uiState.activeUserProfile?.sportType ?: SportType.BADMINTON,
            )
        },
        navigateToRanking = navigateToRanking,
        navigateToMatchingAccepted = navigateToMatchingAccepted,
        navigateToUserProfile = navigateToUserProfile,
        navigateToSportAdd = navigateToSportAdd,
        navigateToSearch = navigateToSearch,
        navigateToSubmit = navigateToSubmit,
        navigateToConfirm = navigateToConfirm,
        onSportsChipClick = viewModel::fetchSelectSportProfile,
        recommendedUserListState = recommendedUserListState,
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeContract.State,
    navigateToNotice: () -> Unit,
    navigateToRegionChange: () -> Unit,
    navigateToTierInfo: () -> Unit,
    navigateToRanking: () -> Unit,
    navigateToMatchingAccepted: () -> Unit,
    navigateToUserProfile: (String) -> Unit,
    navigateToSportAdd: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToSubmit: (
        gameId: String,
        opponentUserId: String,
        opponentNickname: String,
        isFirstAttempt: Boolean,
    ) -> Unit,
    navigateToConfirm: (
        submissionId: String,
        gameId: String,
        isFirstAttempt: Boolean,
    ) -> Unit,
    onSportsChipClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    recommendedUserListState: LazyListState = rememberLazyListState(),
) {
    val activeUserProfile = uiState.activeUserProfile ?: run {
        // TODO: 로딩 또는 에러 UI 표시
        return
    }

    var isDropdownExpanded by remember { mutableStateOf(false) }
    var topBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val scrollState = rememberScrollState()

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
                onChangeRegion = navigateToRegionChange,
                onClickSportChip = { isDropdownExpanded = !isDropdownExpanded },
                onClickNotice = navigateToNotice,
                isNotice = uiState.isNotice,
            )
        }

        HomeDropdown(
            isExpanded = isDropdownExpanded,
            activeSport = uiState.activeUserProfile.sportType,
            sportList = uiState.allUserProfiles.toImmutableList(),
            tierType = uiState.activeUserProfile.tierType,
            lp = uiState.activeUserProfile.lp,
            minLp = uiState.activeUserProfile.minLp,
            maxLp = uiState.activeUserProfile.maxLp,
            winCount = uiState.activeUserProfile.wins,
            loseCount = uiState.activeUserProfile.losses,
            onSportChipClick = { profileId ->
                onSportsChipClick(profileId)
                isDropdownExpanded = false
            },
            onSportAddClick = if (uiState.allUserProfiles.size >= 3) {
                null
            } else {
                {
                    navigateToSportAdd()
                    isDropdownExpanded = false
                }
            },
            onTierClick = {
                navigateToTierInfo()
            },
            onDismiss = {
                isDropdownExpanded = false
            },
            triggerHeight = topBarHeight,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(
                        horizontal = 16.dp,
                    )
                    .padding(
                        top = 12.dp,
                        bottom = 22.dp
                    )
                    .navigationBarsPadding(),
            ) {
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
                                style = SmashingTheme.typography.lg.semibold18,
                                color = SmashingTheme.colors.txtPrimary,
                            )
                            Text(
                                text = stringResource(R.string.home_clos_matching_txt),
                                style = SmashingTheme.typography.md.medium16,
                                color = SmashingTheme.colors.txtPrimary,
                            )
                        }

                        Text(
                            text = stringResource(R.string.home_all_text),
                            style = SmashingTheme.typography.sm.medium14,
                            color = SmashingTheme.colors.txtTertiary,
                            modifier = Modifier
                                .noRippleClickable(
                                    onClick = navigateToMatchingAccepted
                                ),
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    CloseMatching(
                        myProfileId = uiState.activeUserProfile.profileId,
                        myNickname = uiState.activeUserProfile.nickname,
                        matchedUser = uiState.matchedUser,
                        onClick = { matching ->
                            when (matching.resultStatus) {
                                GameResultStatusType.PENDING_RESULT -> {
                                    navigateToSubmit(
                                        matching.gameId,
                                        matching.userId,
                                        matching.nickname,
                                        true,
                                    )
                                }

                                GameResultStatusType.RESULT_REJECTED -> {
                                    navigateToSubmit(
                                        matching.gameId,
                                        matching.userId,
                                        matching.nickname,
                                        false,
                                    )
                                }

                                GameResultStatusType.WAITING_CONFIRMATION -> {
                                    matching.latestSubmissionId?.let { submissionId ->
                                        val isFirstAttempt = matching.latestAttemptNo == 1
                                        navigateToConfirm(
                                            submissionId,
                                            matching.gameId,
                                            isFirstAttempt,
                                        )
                                    }
                                }

                                else -> Unit
                            }
                        },
                        navigateToSearch = navigateToSearch,
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
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
                    if (uiState.recommendedUserList.isNotEmpty()) {
                        LazyRow(
                            state = recommendedUserListState,
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            items(
                                items = uiState.recommendedUserList,
                                key = { it.userId }
                            ) { user ->
                                val cardState = MatchingCardState.Search(
                                    userId = user.userId,
                                    nickname = user.nickname,
                                    genderType = user.gender,
                                    tierType = user.tierType,
                                    onProfileClick = { navigateToUserProfile(user.userId) },
                                    winCount = user.wins,
                                    loseCount = user.losses,
                                    reviewCount = user.reviews,
                                )
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
                Spacer(modifier = Modifier.height(32.dp))

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
                                    onClick = navigateToRanking
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
                            onClick = { navigateToUserProfile(ranker.userId) },
                        )
                    }
                }
            }
            if (isDropdownExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = SmashingTheme.colors.bgDimmed)
                        .noRippleClickable(onClick = { isDropdownExpanded = false })
                )
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
    myNickname: String,
    myProfileId: String,
    onClick: (AcceptedMatching) -> Unit,
    modifier: Modifier = Modifier,
    matchedUser: AcceptedMatching? = null,
    navigateToSearch: () -> Unit,
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
                bottom = 22.dp,
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
                        top = 37.dp,
                        bottom = 23.dp,
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
                modifier = Modifier.fillMaxWidth(),
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
                placeholderDrawable = ProfileImageProvider.getTempImg(nickname),
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
                UserRank(
                    userId = "user1",
                    nickname = "1위 유저",
                    rank = 1,
                    tier = TierType.CHALLENGER,
                    lp = 2500,
                ),
                UserRank(
                    userId = "user2",
                    nickname = "열글자테스트중입니다",
                    rank = 2,
                    tier = TierType.CHALLENGER,
                    lp = 2450,
                ),
                UserRank(
                    userId = "user3",
                    nickname = "1위 유저",
                    rank = 3,
                    tier = TierType.CHALLENGER,
                    lp = 2400,
                ),
                UserRank(
                    userId = "user4",
                    nickname = "프로게이머",
                    rank = 4,
                    tier = TierType.DIAMOND_1,
                    lp = 2350,
                ),
                UserRank(
                    userId = "user5",
                    nickname = "랭커킹커",
                    rank = 5,
                    tier = TierType.DIAMOND_1,
                    lp = 2300,
                ),
                UserRank(
                    userId = "user6",
                    nickname = "승리만추구",
                    rank = 6,
                    tier = TierType.DIAMOND_2,
                    lp = 2250,
                ),
                UserRank(
                    userId = "user7",
                    nickname = "플래티넘마스터",
                    rank = 7,
                    tier = TierType.DIAMOND_2,
                    lp = 2200,
                ),
                UserRank(
                    userId = "user8",
                    nickname = "골드라이더",
                    rank = 8,
                    tier = TierType.DIAMOND_3,
                    lp = 2150,
                ),
                UserRank(
                    userId = "user9",
                    nickname = "실버도전자",
                    rank = 9,
                    tier = TierType.PLATINUM_1,
                    lp = 2100,
                ),
                UserRank(
                    userId = "user10",
                    nickname = "브론즈탈출",
                    rank = 10,
                    tier = TierType.PLATINUM_2,
                    lp = 2050,
                ),
            ).toImmutableList(),
            recommendedUserList = listOf(
                SearchMainItemModel(
                    userId = "match1",
                    nickname = "탁구의신",
                    gender = GenderType.MALE,
                    tierType = TierType.DIAMOND_1,
                    wins = 254,
                    losses = 38,
                    reviews = 32,
                ),
                SearchMainItemModel(
                    userId = "match2",
                    nickname = "테니스마스터",
                    gender = GenderType.FEMALE,
                    tierType = TierType.PLATINUM_2,
                    wins = 180,
                    losses = 45,
                    reviews = 28,
                ),
                SearchMainItemModel(
                    userId = "match3",
                    nickname = "배드민턴킹",
                    gender = GenderType.MALE,
                    tierType = TierType.GOLD_1,
                    wins = 150,
                    losses = 60,
                    reviews = 25,
                ),
            ).toImmutableList(),
            matchedUser = null,
            loadState = HomeUiState.Success,
            isNotice = true,
        ),
        navigateToNotice = {},
        navigateToRegionChange = {},
        navigateToTierInfo = {},
        navigateToRanking = {},
        navigateToMatchingAccepted = {},
        navigateToUserProfile = {},
        onSportsChipClick = {},
        navigateToSportAdd = {},
        navigateToSearch = {},
        navigateToSubmit = { _, _, _, _ -> },
        navigateToConfirm = { _, _, _ -> },
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
                UserRank(
                    userId = "user1",
                    nickname = "1위 유저",
                    rank = 1,
                    tier = TierType.CHALLENGER,
                    lp = 2500,
                ),
            ).toImmutableList(),
            recommendedUserList = listOf<SearchMainItemModel>().toImmutableList(),
            loadState = HomeUiState.Success,
            isNotice = false,
        ),
        navigateToNotice = {},
        navigateToRegionChange = {},
        navigateToTierInfo = {},
        navigateToRanking = {},
        navigateToMatchingAccepted = {},
        navigateToUserProfile = {},
        navigateToSportAdd = {},
        navigateToSearch = {},
        onSportsChipClick = {},
        navigateToSubmit = { _, _, _, _ -> },
        navigateToConfirm = { _, _, _ -> },
    )
}
