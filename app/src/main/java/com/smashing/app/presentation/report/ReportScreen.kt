package com.smashing.app.presentation.report

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.drawable.ic_radio_empty
import com.smashing.app.R.drawable.ic_radio_fill
import com.smashing.app.R.string.report_btn_submit
import com.smashing.app.R.string.report_description
import com.smashing.app.R.string.report_placeholder
import com.smashing.app.R.string.report_title
import com.smashing.app.R.string.report_toast_submitted
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.textfield.SmashingAreaTextField
import com.smashing.app.core.designsystem.component.toast.LocalToastTrigger
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.state.TopBarState
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.presentation.report.type.ReportType

@Composable
fun ReportRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReportViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val showToast = LocalToastTrigger.current
    val reportSubmittedMessage = stringResource(report_toast_submitted)

    ReportScreen(
        uiState = uiState,
        detailTextFieldState = viewModel.detailTextFieldState,
        onReportTypeSelected = viewModel::updateSelectedReportType,
        onReportClick = {
            showToast(reportSubmittedMessage)
            navigateUp()
        },
        navigateUp = navigateUp,
        modifier = modifier,
    )
}

@Composable
private fun ReportScreen(
    uiState: ReportContract.State,
    detailTextFieldState: TextFieldState,
    onReportTypeSelected: (ReportType) -> Unit,
    onReportClick: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        SmashingDefaultTopBar(
            state = TopBarState.Close(
                title = stringResource(report_title),
                onCloseClick = navigateUp,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = stringResource(report_description),
                style = SmashingTheme.typography.lg.semibold18,
                color = SmashingTheme.colors.txtPrimary,
                textAlign = TextAlign.Start,
            )

            Spacer(modifier = Modifier.height(28.dp))

            ReportType.entries.forEachIndexed { index, reportType ->
                ReportTypeItem(
                    reportType = reportType,
                    isSelected = uiState.selectedReportType == reportType,
                    onClick = { onReportTypeSelected(reportType) },
                )
                if (index < ReportType.entries.lastIndex) {
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            SmashingAreaTextField(
                state = detailTextFieldState,
                placeholder = stringResource(report_placeholder),
                enabled = uiState.selectedReportType == ReportType.ETC,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        SmashingButton(
            buttonStyle = ButtonStyle.PRIMARY_WITH_DISABLED,
            text = stringResource(report_btn_submit),
            onClick = onReportClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 48.dp),
            isEnabled = when {
                uiState.selectedReportType == null -> false
                uiState.selectedReportType == ReportType.ETC -> detailTextFieldState.text.toString().isNotBlank()
                else -> true
            },
        )
    }
}

@Composable
private fun ReportTypeItem(
    reportType: ReportType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                if (isSelected) ic_radio_fill else ic_radio_empty,
            ),
            contentDescription = null,
            tint = SmashingTheme.colors.iconPrimary,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = stringResource(reportType.textResId),
            color = SmashingTheme.colors.txtSecondary,
            style = SmashingTheme.typography.md.medium16,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportScreenPreview() {
    var selectedReportType by remember { mutableStateOf<ReportType?>(ReportType.BAD_MANNERS) }
    val detailState = rememberTextFieldState()

    SmashingAndroidTheme {
        ReportScreen(
            uiState = ReportContract.State(selectedReportType = selectedReportType),
            detailTextFieldState = detailState,
            onReportTypeSelected = { selectedReportType = it },
            onReportClick = { selectedReportType = null },
            navigateUp = {},
        )
    }
}