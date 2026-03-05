package com.smashing.app.presentation.mypage


import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.BuildConfig
import com.smashing.app.R.drawable.ic_arrow_next
import com.smashing.app.R.string.mypage
import com.smashing.app.R.string.mypage_account_logout
import com.smashing.app.R.string.mypage_account_manage
import com.smashing.app.R.string.mypage_account_withdraw
import com.smashing.app.R.string.mypage_info_version
import com.smashing.app.R.string.mypage_policy_privacy
import com.smashing.app.R.string.mypage_policy_terms
import com.smashing.app.R.string.mypage_policy_title
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.presentation.mypage.component.MyPageCard


@Composable
fun MyPageRoute(
    navigateUp: () -> Unit,
    navigateToMyProfile: () -> Unit,
    navigateToLogout: () -> Unit,
    navigateToWithDraw: () -> Unit,
    navigateToPolicyPrivacy: () -> Unit,
    navigateToPolicyTerms: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
    }
    LaunchedEffect(Unit) {
        viewModel.fetchProfileInfo()
    }

    MyPageScreen(
        modifier = modifier,
        uiState = uiState,
        onMyProfileClick = navigateToMyProfile,
        onLogoutClick = navigateToLogout,
        onWithDrawClick = navigateToWithDraw,
        onPolicyPrivacyClick = navigateToPolicyPrivacy,
        onPolicyTermsClick = navigateToPolicyTerms,
        onBackClick = navigateUp,
        scrollState = rememberScrollState(),

    )
}

@Composable
private fun MyPageScreen(
    uiState: MyPageContract.State,
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onWithDrawClick: () -> Unit,
    onPolicyPrivacyClick: () -> Unit,
    onPolicyTermsClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.bgCanvas),
    ) {

        SmashingDefaultTopBar(
            title = stringResource(mypage),
            topBarType = TopBarType.BACK,
            onClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
        ) {
            MyPageCard(
                nickname = uiState.myProfileInfo.nickname,
                tierType = uiState.activeProfile.tierType,
                onClick = onMyProfileClick,
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(mypage_account_manage),
                style = typography.xs.medium12,
                color = colors.txtTertiary,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(mypage_account_logout),
                style = typography.sm.medium14,
                color = colors.txtPrimary,
                modifier = Modifier
                    .noRippleClickable(onClick = onLogoutClick)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(mypage_account_withdraw),
                    style = typography.sm.medium14,
                    color = colors.txtPrimary,
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_next),
                    contentDescription = null,
                    tint = colors.iconPrimary,
                    modifier = Modifier
                        .noRippleClickable(onClick = onWithDrawClick)
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
            HorizontalDivider(
                thickness = 2.dp,
                color = colors.bgSurface,
            )
            Spacer(modifier = Modifier.height(26.dp))
            Text(
                text = stringResource(mypage_policy_title),
                style = typography.xs.medium12,
                color = colors.txtTertiary,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(mypage_policy_privacy),
                style = typography.sm.medium14,
                color = colors.txtPrimary,
                modifier = Modifier
                    .noRippleClickable(onClick = onPolicyPrivacyClick)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(mypage_policy_terms),
                style = typography.sm.medium14,
                color = colors.txtPrimary,
                modifier = Modifier
                    .noRippleClickable(onClick = onPolicyTermsClick)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(mypage_info_version),
                    style = typography.sm.medium14,
                    color = colors.txtPrimary,
                )
                Spacer(modifier = Modifier.weight(1f))
                //버전 정보
                Text(
                    text = BuildConfig.VERSION_NAME,
                    style = typography.sm.medium14,
                    color = colors.txtTertiary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    SmashingAndroidTheme {
        MyPageScreen(
            uiState = MyPageContract.State(),
            onWithDrawClick = {},
            onPolicyPrivacyClick = {},
            onPolicyTermsClick = {},
            onLogoutClick = {},
            onMyProfileClick = {},
            onBackClick = {},
        )
    }
}
