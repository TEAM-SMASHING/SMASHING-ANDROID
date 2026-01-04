package com.smashing.app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.core.common.state.UiState
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.data.model.DummyUser
import com.smashing.app.presentation.home.component.DummyUserItem
import com.smashing.app.presentation.login.KakaoLoginManager
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeRoute(
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchDummyUsers()
    }
    HomeScreen(
        uiState = uiState,
        modifier = modifier,
        onKakaoLogoutClick = { KakaoLoginManager(context).logOutKakao(navigateToLogin) },
        onKakaoUnlinkClick = { KakaoLoginManager(context).unlinkKakao(navigateToLogin) },
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeContract.State,
    modifier: Modifier = Modifier,
    onKakaoLogoutClick: () -> Unit = {},
    onKakaoUnlinkClick: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when (uiState.dummyUsersLoadState) {
            is UiState.Idle -> {
                // 빈 상태 화면
            }

            is UiState.Failure -> {
                // 에러 상태 화면
                KakaoButton(
                    onKakaoLogoutClick = onKakaoLogoutClick,
                    onKakaoUnlinkClick = onKakaoUnlinkClick,
                )
            }

            is UiState.Loading -> {
                //로딩 상태 화면
            }

            is UiState.Success -> {
                DummyUserListColumn(
                    dummyUsers = uiState.dummyUsersLoadState.data,
                    modifier = modifier,
                )
                KakaoButton(
                    onKakaoLogoutClick = onKakaoLogoutClick,
                    onKakaoUnlinkClick = onKakaoUnlinkClick,
                )
            }
        }
    }
}

@Composable
private fun DummyUserListColumn(
    dummyUsers: ImmutableList<DummyUser>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(
            dummyUsers, key = { it.id },
        ) { user ->
            DummyUserItem(
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                profileImage = user.profileImage,
            )
        }
    }
}

@Composable
private fun KakaoButton(
    modifier: Modifier = Modifier,
    onKakaoLogoutClick: () -> Unit,
    onKakaoUnlinkClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .size(70.dp, 50.dp)
                .background(color = Color.Black)
                .noRippleClickable(onClick = onKakaoLogoutClick),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "로그아웃",
                color = Color.White,
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .size(70.dp, 50.dp)
                .background(color = Color.Black)
                .noRippleClickable(onClick = onKakaoUnlinkClick),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "회원 탈퇴",
                color = Color.White,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DummyScreenPreview() {
    DummyUserListColumn(
        dummyUsers = persistentListOf(),
    )
}
