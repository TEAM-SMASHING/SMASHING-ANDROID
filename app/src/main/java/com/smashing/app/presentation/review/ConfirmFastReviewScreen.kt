//package com.smashing.app.presentation.review.component
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.systemBarsPadding
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.smashing.app.core.designsystem.component.topbar.SmashingDefaultTopBar
//import com.smashing.app.core.designsystem.style.TopBarType
//import com.smashing.app.presentation.submit.SubmitContract
//import com.smashing.app.presentation.submit.SubmitViewModel
//import com.smashing.app.R.string.review
//import com.smashing.app.core.designsystem.theme.SmashingTheme
//
//
//@Composable
//fun ConfirmFastReviewRoute(
//    navigateUp: () -> Unit,
//    modifier: Modifier = Modifier,
//    viewModel: SubmitViewModel = hiltViewModel(),
//) {
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//
//    ConfirmFastReviewScreen(
//        uiState = uiState,
//        modifier = modifier,
//       // onBackClick = navigateUp,
//        //onDenyClick = { /* 아니요 로직 */ },
//        onConfirmClick = { /* 네, 맞아요 로직 */ }
//    )
//}
//
//@Composable
//private fun ConfirmFastReviewScreen(
//    uiState: SubmitContract.State,
//    onConfirmClick: () -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(horizontal = 16.dp)
//            .systemBarsPadding(),
//    ) {
//        SmashingDefaultTopBar(
//            title = stringResource(review),
//            topBarType = TopBarType.DEFAULT,
//            onClick = null,
//        )
//        Column(
//            modifier = modifier,
//            ) {
//            Text(
//                text ="님이 " +
//                        "보낸 후기가 도착했어요",
//                color = SmashingTheme.colors.txtPrimary,
//                style = SmashingTheme.typography.xl.semibold20,
//            )
//            Spacer(modifier= Modifier.padding(vertical = 24.dp))
//
//
//            ConfirmReviewCard(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//            )
//
//        }
//    }
//}
