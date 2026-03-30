package com.smashing.app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.drawable.ic_info
import com.smashing.app.R.string.home_all_text
import com.smashing.app.R.string.home_close_matching_txt
import com.smashing.app.R.string.home_greeting_with_nickname
import com.smashing.app.R.string.home_new_matching_txt
import com.smashing.app.R.string.home_no_user
import com.smashing.app.R.string.home_recommend_title_with_nickname
import com.smashing.app.R.string.home_region_ranker
import com.smashing.app.core.designsystem.component.card.MatchingCard
import com.smashing.app.core.designsystem.component.ranking.SmashingRankingItem
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.core.designsystem.style.TierInfoStyle
import com.smashing.app.core.designsystem.style.toTierInfoStyle
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.home.MyProfileTierInfo
import com.smashing.app.data.model.rank.UserRank
import com.smashing.app.data.model.search.SearchMainItemModel
import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import com.smashing.app.presentation.home.component.CloseMatching
import com.smashing.app.presentation.home.component.HomeDropdown
import com.smashing.app.presentation.home.component.HomeTopBar
import com.smashing.app.presentation.home.component.RecommendedInfoPopup
import kotlinx.collections.immutable.toImmutableList


@Composable
fun HomeRoute(
    navigateToNotice: () -> Unit,
    navigateToRegionChange: () -> Unit,
    navigateToTierInfo: (TierInfoStyle, SportType) -> Unit,
    navigateToRanking: () -> Unit,
    navigateToMatchingAccepted: () -> Unit,
    navigateToUserProfile: (String) -> Unit,
    navigateToSportAdd: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToSubmit: (
        gameId: String,
        opponentUserProfileId: String,
        opponentNickname: String,
        isFirstAttempt: Boolean,
        submissionId: String?,
    ) -> Unit,
    navigateToConfirm: (
        submissionId: String,
        gameId: String,
        isFirstAttempt: Boolean,
    ) -> Unit,
    navigateToMyProfile: () -> Unit,
    navigateToMyPage: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val recommendedUserListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.fetchHome()
    }

    LaunchedEffect(uiState.recommendedUserList) {
        recommendedUserListState.scrollToItem(0)
    }

    HomeScreen(
        uiState = uiState,
        navigateToNotice = navigateToNotice,
        navigateToRegionChange = navigateToRegionChange,
        navigateToTierInfo = {
            navigateToTierInfo(
                uiState.activeMyProfile?.myProfileInfo?.tierType?.toTierInfoStyle()
                    ?: TierInfoStyle.IRON,
                uiState.activeMyProfile?.myProfileInfo?.sportType ?: SportType.BADMINTON,
            )
        },
        navigateToRanking = navigateToRanking,
        navigateToMatchingAccepted = navigateToMatchingAccepted,
        navigateToUserProfile = navigateToUserProfile,
        navigateToSportAdd = navigateToSportAdd,
        navigateToSearch = navigateToSearch,
        navigateToSubmit = navigateToSubmit,
        navigateToConfirm = navigateToConfirm,
        navigateToMyProfile = navigateToMyProfile,
        onSportsChipClick = viewModel::fetchSelectSportProfile,
        recommendedUserListState = recommendedUserListState,
        modifier = modifier,
        navigateToMyPage = navigateToMyPage,
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
        opponentUserProfileId: String,
        opponentNickname: String,
        isFirstAttempt: Boolean,
        submissionId: String?,
    ) -> Unit,
    navigateToConfirm: (
        submissionId: String,
        gameId: String,
        isFirstAttempt: Boolean,
    ) -> Unit,
    navigateToMyProfile: () -> Unit,
    onSportsChipClick: (String) -> Unit,
    navigateToMyPage: () -> Unit,
    modifier: Modifier = Modifier,
    recommendedUserListState: LazyListState = rememberLazyListState(),
) {
    if (uiState.activeMyProfile == null) return

    var isDropdownExpanded by remember { mutableStateOf(false) }
    var topBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    var isInfoPopupVisible by remember { mutableStateOf(false) }

    var popupOffset by remember { mutableStateOf(Offset.Zero) }
    var popupWidth by remember { mutableStateOf(0.dp) }


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
                userRegion = uiState.activeMyProfile.region,
                userSport = uiState.activeMyProfile.myProfileInfo.sportType,
                userTier = uiState.activeMyProfile.myProfileInfo.tierType,
                onClickRegion = {},
                onChangeRegion = navigateToRegionChange,
                onClickSportChip = { isDropdownExpanded = !isDropdownExpanded },
                onClickNotice = navigateToNotice,
                isNotice = uiState.isNotice,
                onMyPageClick = navigateToMyPage,
            )
        }

        HomeDropdown(
            isExpanded = isDropdownExpanded,
            activeSport = uiState.activeMyProfile.myProfileInfo.sportType,
            sportList = uiState.activeMyProfile.myProfileItem.toImmutableList(),
            tierType = uiState.activeMyProfile.myProfileInfo.tierType,
            lp = uiState.activeMyProfile.myProfileInfo.lp,
            minLp = uiState.activeMyProfile.myProfileInfo.minLp,
            maxLp = uiState.activeMyProfile.myProfileInfo.maxLp,
            winCount = uiState.activeMyProfile.myProfileInfo.winCount,
            loseCount = uiState.activeMyProfile.myProfileInfo.loseCount,
            onSportChipClick = { profileId ->
                onSportsChipClick(profileId)
                isDropdownExpanded = false
            },
            onSportAddClick = if (uiState.activeMyProfile.myProfileItem.size >= 3) {
                null
            } else {
                {
                    navigateToSportAdd()
                    isDropdownExpanded = false
                }
            },
            onTierClick = navigateToTierInfo,
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
                        top = 12.dp,
                        bottom = 20.dp
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Column {
                            Text(
                                text = stringResource(
                                    home_greeting_with_nickname,
                                    uiState.activeMyProfile.nickname,
                                ),
                                style = SmashingTheme.typography.lg.semibold18,
                                color = SmashingTheme.colors.txtPrimary,
                            )
                            Text(
                                text = if (uiState.matchedUser != null) stringResource(
                                    home_close_matching_txt
                                ) else stringResource(home_new_matching_txt),
                                style = SmashingTheme.typography.md.medium16,
                                color = SmashingTheme.colors.txtPrimary,
                            )
                        }

                        Text(
                            text = stringResource(home_all_text),
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
                        myNickname = uiState.activeMyProfile.nickname,
                        matchedUser = uiState.matchedUser,
                        onClick = { matching ->
                            when (matching.resultStatus) {
                                GameResultStatusType.PENDING_RESULT -> {
                                    navigateToSubmit(
                                        matching.gameId,
                                        matching.profileId,
                                        matching.nickname,
                                        true,
                                        matching.latestSubmissionId,
                                    )
                                }

                                GameResultStatusType.RESULT_REJECTED -> {
                                    navigateToSubmit(
                                        matching.gameId,
                                        matching.profileId,
                                        matching.nickname,
                                        false,
                                        matching.latestSubmissionId,
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
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                            ),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(
                                home_recommend_title_with_nickname,
                                uiState.activeMyProfile.nickname,
                            ),
                            style = SmashingTheme.typography.lg.semibold18,
                            color = SmashingTheme.colors.txtPrimary,
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            imageVector = ImageVector.vectorResource(ic_info),
                            contentDescription = null,
                            tint = SmashingTheme.colors.iconTertiary,
                            modifier = Modifier
                                .onGloballyPositioned { coordinates ->
                                    val position = coordinates.positionInWindow()
                                    val iconHeight = coordinates.size.height
                                    val iconWidth = coordinates.size.width
                                    popupOffset = Offset(
                                        x = position.x + iconWidth / 2,
                                        y = position.y + iconHeight
                                    )
                                }
                                .noRippleClickable(
                                    onClick = { isInfoPopupVisible = !isInfoPopupVisible }
                                ),
                        )

                    }

                    if (uiState.recommendedUserList.isNotEmpty()) {
                        LazyRow(
                            state = recommendedUserListState,
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentPadding = PaddingValues(
                                horizontal = 16.dp,
                            ),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            items(
                                items = uiState.recommendedUserList,
                                key = { it.userProfileId }
                            ) { user ->
                                val cardState = MatchingCardState.Search(
                                    profileId = user.userProfileId,
                                    nickname = user.nickname,
                                    genderType = user.gender,
                                    tierType = user.tierType,
                                    onProfileClick = { navigateToUserProfile(user.userProfileId) },
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
                            text = stringResource(home_no_user),
                            style = SmashingTheme.typography.md.medium16,
                            color = SmashingTheme.colors.txtTertiary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .background(
                                    color = SmashingTheme.colors.bgSurface,
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .padding(
                                    vertical = 31.dp,
                                    horizontal = 16.dp,
                                ),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
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
                            text = stringResource(home_region_ranker),
                            style = SmashingTheme.typography.lg.semibold18,
                            color = SmashingTheme.colors.txtPrimary,
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = stringResource(home_all_text),
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
                            userProfileId = ranker.userProfileId,
                            onClick = {
                                if (ranker.userProfileId != uiState.activeMyProfile.myProfileInfo.profileId) {
                                    navigateToUserProfile(ranker.userProfileId)
                                } else {
                                    navigateToMyProfile()
                                }
                            },
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
        if (isInfoPopupVisible) {
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(
                    x = with(density) {
                        (popupOffset.x - popupWidth.toPx() / 2).toInt()
                    },
                    y = with(density) {
                        (popupOffset.y + 9.dp.toPx()).toInt()
                    }
                ),
                onDismissRequest = { isInfoPopupVisible = false },
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                ),
            ) {
                RecommendedInfoPopup(
                    onDismiss = { isInfoPopupVisible = false },
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        popupWidth = with(density) {
                            coordinates.size.width.toDp()
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeContract.State(
            activeMyProfile = MyProfileTierInfo(
                nickname = "Test",
                region = "서울",
                myProfileInfo = ProfileInfo(
                    profileId = "0USP111222333",
                    sportType = SportType.TENNIS,
                    tierType = TierType.GOLD_1,
                    lp = 123,
                    minLp = 100,
                    maxLp = 500,
                    winCount = 10,
                    loseCount = 7,
                ),
                myProfileItem = listOf(),
            ),
            topRankerList = listOf(
                UserRank(
                    userProfileId = "user1",
                    nickname = "1위 유저",
                    rank = 1,
                    tier = TierType.CHALLENGER,
                    lp = 2500,
                ),
                UserRank(
                    userProfileId = "user2",
                    nickname = "열글자테스트중입니다",
                    rank = 2,
                    tier = TierType.CHALLENGER,
                    lp = 2450,
                ),
                UserRank(
                    userProfileId = "user3",
                    nickname = "1위 유저",
                    rank = 3,
                    tier = TierType.CHALLENGER,
                    lp = 2400,
                ),
                UserRank(
                    userProfileId = "user4",
                    nickname = "프로게이머",
                    rank = 4,
                    tier = TierType.DIAMOND_1,
                    lp = 2350,
                ),
                UserRank(
                    userProfileId = "user5",
                    nickname = "랭커킹커",
                    rank = 5,
                    tier = TierType.DIAMOND_1,
                    lp = 2300,
                ),
                UserRank(
                    userProfileId = "user6",
                    nickname = "승리만추구",
                    rank = 6,
                    tier = TierType.DIAMOND_2,
                    lp = 2250,
                ),
                UserRank(
                    userProfileId = "user7",
                    nickname = "플래티넘마스터",
                    rank = 7,
                    tier = TierType.DIAMOND_2,
                    lp = 2200,
                ),
                UserRank(
                    userProfileId = "user8",
                    nickname = "골드라이더",
                    rank = 8,
                    tier = TierType.DIAMOND_3,
                    lp = 2150,
                ),
                UserRank(
                    userProfileId = "user9",
                    nickname = "실버도전자",
                    rank = 9,
                    tier = TierType.PLATINUM_1,
                    lp = 2100,
                ),
                UserRank(
                    userProfileId = "user10",
                    nickname = "브론즈탈출",
                    rank = 10,
                    tier = TierType.PLATINUM_2,
                    lp = 2050,
                ),
            ).toImmutableList(),
            recommendedUserList = listOf(
                SearchMainItemModel(
                    userProfileId = "match1",
                    nickname = "탁구의신",
                    gender = GenderType.MALE,
                    tierType = TierType.DIAMOND_1,
                    wins = 254,
                    losses = 38,
                    reviews = 32,
                ),
                SearchMainItemModel(
                    userProfileId = "match2",
                    nickname = "테니스마스터",
                    gender = GenderType.FEMALE,
                    tierType = TierType.PLATINUM_2,
                    wins = 180,
                    losses = 45,
                    reviews = 28,
                ),
                SearchMainItemModel(
                    userProfileId = "match3",
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
        navigateToSportAdd = {},
        navigateToSearch = {},
        navigateToSubmit = { _, _, _, _, _ -> },
        navigateToConfirm = { _, _, _ -> },
        navigateToMyProfile = {},
        onSportsChipClick = {},
        navigateToMyPage = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenEmptyValuePreview() {
    HomeScreen(
        uiState = HomeContract.State(
            activeMyProfile = MyProfileTierInfo(
                nickname = "Test",
                region = "서울",
                myProfileInfo =
                    ProfileInfo(
                        profileId = "0USP111222333",
                        sportType = SportType.TENNIS,
                        tierType = TierType.GOLD_1,
                        lp = 123,
                        minLp = 100,
                        maxLp = 500,
                        winCount = 10,
                        loseCount = 7,
                    ),
                myProfileItem = listOf(),
            ),
            topRankerList = listOf(
                UserRank(
                    userProfileId = "user1",
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
        navigateToMyProfile = {},
        navigateToSubmit = { _, _, _, _, _ -> },
        navigateToConfirm = { _, _, _ -> },
        onSportsChipClick = {},
        navigateToMyPage = {},
    )
}
