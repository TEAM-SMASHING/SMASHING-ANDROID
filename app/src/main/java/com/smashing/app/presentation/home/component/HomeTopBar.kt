package com.smashing.app.presentation.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_bell
import com.smashing.app.R.drawable.ic_bell_notification
import com.smashing.app.core.designsystem.component.dropdown.RegionDropdown
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeTopBar(
    userRegion: String,
    userSport: SportType,
    userTier: TierType,
    onClickRegion: (String) -> Unit,
    onChangeRegion: () -> Unit,
    onClickSportChip: () -> Unit,
    onClickNotice: () -> Unit,
    modifier: Modifier = Modifier,
    isNotice: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RegionDropdown(
            selectedItem = userRegion,
            items = listOf(
                userRegion
            ).toImmutableList(),
            onClick = onClickRegion,
            onRegionChange = onChangeRegion,
            isDivide = true,
            modifier = Modifier
                .padding(vertical = 12.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        SportsTierChip(
            sportType = userSport,
            tierType = userTier,
            onClick = onClickSportChip,
        )

        Spacer(modifier = Modifier.width(12.dp))

        if (!isNotice) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_bell),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .noRippleClickable(
                        onClick = onClickNotice,
                    )
            )
        } else {
            Icon(
                imageVector = ImageVector.vectorResource(ic_bell_notification),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .noRippleClickable(
                        onClick = onClickNotice,
                    )
            )
        }
    }
}