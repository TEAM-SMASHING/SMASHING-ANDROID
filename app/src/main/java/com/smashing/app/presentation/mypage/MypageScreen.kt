package com.smashing.app.presentation.mypage


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.BuildConfig
import com.smashing.app.R.drawable.ic_arrow_next
import com.smashing.app.R.string.cancel_short
import com.smashing.app.R.string.mypage
import com.smashing.app.R.string.mypage_account_logout
import com.smashing.app.R.string.mypage_account_manage
import com.smashing.app.R.string.mypage_account_withdraw
import com.smashing.app.R.string.mypage_info_version
import com.smashing.app.R.string.mypage_logout_message
import com.smashing.app.R.string.mypage_policy_privacy
import com.smashing.app.R.string.mypage_policy_terms
import com.smashing.app.R.string.mypage_policy_title
import com.smashing.app.core.designsystem.component.dialog.SmashingDialog
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.DialogStyle
import com.smashing.app.core.designsystem.style.TopBarStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.core.extension.openUrl
import com.smashing.app.presentation.mypage.component.MyPageProfileHeader


@Composable
fun MyPageRoute(
    navigateUp: () -> Unit,
    navigateToMyProfile: () -> Unit,
    navigateToLogout: () -> Unit,
    navigateToWithDraw: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchProfileInfo()
    }
    var isShowLogoutDialog by remember { mutableStateOf(false) }
    if (isShowLogoutDialog) {
        SmashingDialog(
            title = stringResource(mypage_account_logout),
            subtitle = stringResource(mypage_logout_message),
            type = DialogStyle.ALERT,
            confirmText = stringResource(mypage_account_logout),
            dismissText = stringResource(cancel_short),
            onConfirmClick = {
                isShowLogoutDialog = false
                navigateToLogout()
            },
            onDismissClick = { isShowLogoutDialog = false },
            onDismissRequest = {}
        )
    }

    MyPageScreen(
        modifier = modifier,
        uiState = uiState,
        onMyProfileClick = navigateToMyProfile,
        onLogoutClick = { isShowLogoutDialog = true },
        onWithDrawClick = navigateToWithDraw,
        onPolicyPrivacyClick = { policyPrivacyLink -> context.openUrl(policyPrivacyLink) },
        onPolicyTermsClick = { kakaoLink -> context.openUrl(kakaoLink) },
        onBackClick = navigateUp,
    )
}


@Composable
private fun MyPageScreen(
    uiState: MyPageContract.State,
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onWithDrawClick: () -> Unit,
    modifier: Modifier = Modifier,
    onPolicyPrivacyClick: (String?) -> Unit = {},
    onPolicyTermsClick: (String?) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.bgCanvas)
            .systemBarsPadding(),
    ) {

        SmashingDefaultTopBar(
            title = stringResource(mypage),
            topBarStyle = TopBarStyle.BACK,
            onClick = onBackClick,
        )
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
        ) {
            MyPageProfileHeader(
                nickname = uiState.myPageProfileInfo.nickname,
                tierType = uiState.myPageProfileInfo.tierType,
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
                    .noRippleClickable(onClick = { onPolicyPrivacyClick(policyPrivacyLink) })
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(mypage_policy_terms),
                style = typography.sm.medium14,
                color = colors.txtPrimary,
                modifier = Modifier
                    .noRippleClickable(onClick = { onPolicyTermsClick(policyTermsLink) })
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
                Text(
                    text = "ver ${BuildConfig.VERSION_NAME}",
                    style = typography.sm.medium14,
                    color = colors.txtTertiary,
                )
            }
        }
    }
}

private const val policyPrivacyLink = "https://github.com/TEAM-SMASHING/SMASHING-ANDROID"
private const val policyTermsLink = "https://github.com/TEAM-SMASHING/SMASHING-ANDROID"


@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
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
