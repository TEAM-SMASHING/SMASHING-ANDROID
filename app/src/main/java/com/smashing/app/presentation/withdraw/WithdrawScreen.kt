package com.smashing.app.presentation.withdraw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.state.TopBarState
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.presentation.withdraw.type.WithdrawalDeletedType
import com.smashing.app.R.drawable.ic_checkbox
import com.smashing.app.R.drawable.ic_checkbox_empty

@Composable
fun WithdrawRoute(
    navigateUp: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WithdrawViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WithdrawScreen(
        uiState = uiState,
        onWithdrawalAgreedChange = viewModel::updateWithdrawalAgreed,
        navigateUp = navigateUp,
        navigateToLogin = navigateToLogin,
        modifier = modifier,
    )
}

@Composable
private fun WithdrawScreen(
    uiState: WithdrawContract.State,
    onWithdrawalAgreedChange: (Boolean) -> Unit,
    navigateUp: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        SmashingDefaultTopBar(
            state = TopBarState.Back(
                title = "계정 탈퇴",
                onBackClick = navigateUp,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = "정말 탈퇴하시겠습니까?",
                style = SmashingTheme.typography.xl.semibold20,
                color = SmashingTheme.colors.txtPrimary,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth(),
            )

            Text(
                text = "회원 탈퇴 시 아래 정보가 삭제됩니다",
                style = SmashingTheme.typography.md.medium16,
                color = SmashingTheme.colors.txtPrimary,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(40.dp))

            WithdrawalDeletedType.entries.forEachIndexed { index, item ->
                Text(
                    text = item.text,
                    style = SmashingTheme.typography.sm.medium14,
                    color = SmashingTheme.colors.txtPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = SmashingTheme.colors.bgSurface,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .padding(horizontal = 16.dp, vertical = 15.dp),
                )

                if (index < WithdrawalDeletedType.entries.lastIndex) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = if (uiState.isWithdrawalAgreed) ic_checkbox else ic_checkbox_empty,
                    ),
                    contentDescription = null,
                    tint = SmashingTheme.colors.iconPrimary,
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleClickable(
                            onClick = {
                                onWithdrawalAgreedChange(!uiState.isWithdrawalAgreed)
                            }
                        ),
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "주의사항을 인지하였으며, 이에 동의합니다",
                    style = SmashingTheme.typography.sm.regular14,
                    color = SmashingTheme.colors.txtPrimary,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            SmashingButton(
                buttonStyle = ButtonStyle.WARNING,
                text = "탈퇴하기",
                onClick = {
                    //TODO: 탈퇴하기 로직 추가
                    navigateToLogin()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 49.dp),
                isEnabled = uiState.isWithdrawalAgreed,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WithdrawScreenPreview() {
    var isAgreed by remember { mutableStateOf(false) }

    SmashingAndroidTheme {
        WithdrawScreen(
            uiState = WithdrawContract.State(isWithdrawalAgreed = isAgreed),
            onWithdrawalAgreedChange = { isAgreed = it },
            navigateUp = {},
            navigateToLogin = {},
        )
    }
}
