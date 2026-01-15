package com.smashing.app.presentation.home.regionchange

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.dialog.SmashingDialog
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.DialogStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.domain.model.Region

@Composable
fun RegionChangeRoute(
    modifier: Modifier = Modifier,
    viewModel: RegionChangeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RegionChangeScreen(
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
fun RegionChangeScreen(
    uiState: RegionChangeContract.State,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = SmashingTheme.colors.bgCanvas,
            ),
    ) {
        SmashingDefaultTopBar(
            title = "지역 변경",
            topBarType = TopBarType.CLOSE,
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(13.dp))

        Column(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                ),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "활동지역을 변경해주세요",
                style = SmashingTheme.typography.xl.semibold20,
                color = SmashingTheme.colors.txtPrimary,
            )
            Text(
                text = "서울 소재 주소만 입력가능해요",
                style = SmashingTheme.typography.sm.medium14,
                color = SmashingTheme.colors.txtTertiary,
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = if (uiState.selectedRegion != null) uiState.selectedRegion.addressName else "주소를 검색해주세요",
                style = SmashingTheme.typography.sm.medium14,
                color = if(uiState.selectedRegion != null) SmashingTheme.colors.txtPrimary else SmashingTheme.colors.txtDisabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .border(
                        width = 1.dp,
                        color = SmashingTheme.colors.iconTertiary,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 13.dp,
                    )
            )

            Spacer(modifier = Modifier.weight(1f))

            SmashingButton(
                buttonStyle = if (uiState.selectedRegion != null) ButtonStyle.PRIMARY else ButtonStyle.DISABLED_ACTIVE,
                text = "완료",
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }

    if (showDialog) {
        SmashingDialog(
            title = "지역을 변경하시겠습니까?",
            type = DialogStyle.ALERT,
            confirmText = "예",
            dismissText = "아니요",
            onConfirmClick = {
                showDialog = false
            },
            onDismissClick = {
                showDialog = false
            },
            onDismissRequest = {
                showDialog = false
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegionChangeScreenPreview_Empty() {
    SmashingAndroidTheme {
        RegionChangeScreen(
            uiState = RegionChangeContract.State(
                selectedRegion = null,
                regionLoadState = RegionChangeUiState.Idle,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegionChangeScreenPreview_Selected() {
    SmashingAndroidTheme {
        RegionChangeScreen(
            uiState = RegionChangeContract.State(
                selectedRegion = Region(
                    addressName = "서울특별시 강남구",
                    cityName = "서울특별시",
                    districtName = "강남구",
                ),
                regionLoadState = RegionChangeUiState.Success,
            ),
        )
    }
}