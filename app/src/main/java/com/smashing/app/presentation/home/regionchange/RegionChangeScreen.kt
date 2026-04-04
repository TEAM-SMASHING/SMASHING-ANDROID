package com.smashing.app.presentation.home.regionchange

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smashing.app.R.string.done
import com.smashing.app.R.string.no
import com.smashing.app.R.string.region_change_description
import com.smashing.app.R.string.region_change_confirm
import com.smashing.app.R.string.region_change_dialog_title
import com.smashing.app.R.string.region_change_placeholder
import com.smashing.app.R.string.region_change_subtitle
import com.smashing.app.R.string.region_change_title
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.dialog.SmashingDialog
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.DialogStyle
import com.smashing.app.core.designsystem.state.TopBarState
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.domain.model.Region
import com.smashing.app.presentation.home.regionchange.RegionChangeContract.SideEffect.NavigateToRegion

@Composable
fun RegionChangeRoute(
    navigateToRegion: () -> Unit,
    navigateUp: () -> Unit,
    regionResult: Region?,
    onRegionResultConsumed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegionChangeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(regionResult) {
        if (regionResult != null) {
            viewModel.updateSelectedRegion(regionResult)
            onRegionResultConsumed()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToRegion -> navigateToRegion()
                    is RegionChangeContract.SideEffect.RegionChangeSuccess -> navigateUp()
                }
            }
    }

    RegionChangeScreen(
        uiState = uiState,
        modifier = modifier,
        navigateToRegion = viewModel::updateToRegion,
        onConfirmRegionChange = viewModel::changeRegion,
        navigateUp = navigateUp,
    )
}

@Composable
fun RegionChangeScreen(
    navigateToRegion: () -> Unit,
    navigateUp: () -> Unit,
    onConfirmRegionChange: () -> Unit,
    uiState: RegionChangeContract.State,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = SmashingTheme.colors.bgCanvas,
            )
            .systemBarsPadding(),
    ) {
        SmashingDefaultTopBar(
            state = TopBarState.Close(
                title = stringResource(region_change_title),
                onCloseClick = navigateUp,
            ),
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
                text = stringResource(region_change_description),
                style = SmashingTheme.typography.xl.semibold20,
                color = SmashingTheme.colors.txtPrimary,
            )
            Text(
                text = stringResource(region_change_subtitle),
                style = SmashingTheme.typography.sm.medium14,
                color = SmashingTheme.colors.txtTertiary,
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = uiState.selectedRegion?.addressName
                    ?: stringResource(region_change_placeholder),
                style = SmashingTheme.typography.sm.medium14,
                color = if (uiState.selectedRegion != null) SmashingTheme.colors.txtPrimary else SmashingTheme.colors.txtDisabled,
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
                    .noRippleClickable(
                        onClick = navigateToRegion,
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 13.dp,
                    )
            )

            Spacer(modifier = Modifier.weight(1f))

            SmashingButton(
                buttonStyle = if (uiState.selectedRegion != null) ButtonStyle.PRIMARY else ButtonStyle.PRIMARY_WITH_DISABLED,
                text = stringResource(done),
                onClick = {
                    showDialog = true
                },
                isEnabled = uiState.selectedRegion != null,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }

    if (showDialog) {
        SmashingDialog(
            title = stringResource(region_change_dialog_title),
            onDismissClick = { showDialog = false },
            type = DialogStyle.ALERT,
            confirmText = stringResource(region_change_confirm),
            dismissText = stringResource(no),
            onConfirmClick = {
                showDialog = false
                onConfirmRegionChange()
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
            navigateToRegion = {},
            navigateUp = {},
            onConfirmRegionChange = {}
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
            navigateToRegion = {},
            navigateUp = {},
            onConfirmRegionChange = {},
        )
    }
}
