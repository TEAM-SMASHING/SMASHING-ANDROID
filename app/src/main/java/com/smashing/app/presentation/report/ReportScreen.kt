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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smashing.app.R.drawable.ic_radio_empty
import com.smashing.app.R.drawable.ic_radio_fill
import com.smashing.app.core.designsystem.component.button.SmashingButton
import com.smashing.app.core.designsystem.component.textfield.SmashingAreaTextField
import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.presentation.report.type.ReportType

@Composable
fun ReportRoute(
    navigateUp: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReportScreen(
        uiState = uiState,
        detailTextFieldState = viewModel.detailTextFieldState,
        onReportTypeSelected = viewModel::updateSelectedReportType,
        onDetailTextChange = viewModel::updateEtcText,
        onReportClick = { /* TODO: submit report */ },
        navigateUp = navigateUp,
    )
}

@Composable
fun ReportScreen(
    uiState: ReportContract.State,
    detailTextFieldState: TextFieldState,
    onReportTypeSelected: (ReportType) -> Unit,
    onDetailTextChange: (String) -> Unit,
    onReportClick: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(detailTextFieldState.text) {
        onDetailTextChange(detailTextFieldState.text.toString())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SmashingTheme.colors.bgCanvas),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        SmashingDefaultTopBar(
            title = "신고하기",
            topBarType = TopBarType.CLOSE,
            onClick = navigateUp,
            modifier = Modifier.fillMaxWidth(),
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            Text(
                text = "사용자를 신고하는 이유를 선택해 주세요",
                style = SmashingTheme.typography.md.medium16,
                color = SmashingTheme.colors.txtPrimary,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))

            ReportType.entries.forEach { reportType ->
                ReportTypeItem(
                    reportType = reportType,
                    isSelected = uiState.selectedReportType == reportType,
                    onClick = { onReportTypeSelected(reportType) },
                )
                Spacer(modifier = Modifier.height(28.dp))
            }

            SmashingAreaTextField(
                state = detailTextFieldState,
                placeholder = "신고 사유를 구체적으로 작성해주세요",
            )
        }

        SmashingButton(
            buttonStyle = ButtonStyle.PRIMARY_WITH_DISABLED,
            text = "신고하기",
            onClick = onReportClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp),
            isEnabled = uiState.selectedReportType != null,
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
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = reportType.text,
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
            onDetailTextChange = {},
            onReportClick = { selectedReportType = null },
            navigateUp = {},
        )
    }
}