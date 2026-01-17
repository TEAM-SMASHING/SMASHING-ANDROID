package com.smashing.app.presentation.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_search_lg
import com.smashing.app.R.drawable.ic_bell
import com.smashing.app.core.designsystem.component.dropdown.RegionDropdown
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.extension.noRippleClickable
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SearchTopBar(
    selectedRegion: String,
    onRegionDropdownClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onRegionSelectClick: () -> Unit,
    onReginItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    //val density = LocalDensity.current
    //var isExpanded by remember { mutableStateOf(false) }
    //var triggerWidth by remember { mutableStateOf(0.dp) }
    //var triggerHeight by remember { mutableStateOf(0.dp) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        
        val regionItems = remember {
            listOf("양천구", "강서구", "장신구").toImmutableList()
        }

        RegionDropdown(
            selectedItem = selectedRegion,
            items = regionItems,
            onClick = onRegionDropdownClick,
            onRegionChange = onRegionSelectClick,
            isDivide = false,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(ic_search_lg),
            contentDescription = null,
            tint = colors.iconPrimary,
            modifier = Modifier
                .noRippleClickable(onClick = onSearchClick)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            imageVector = ImageVector.vectorResource(ic_bell),
            contentDescription = null,
            tint = colors.iconPrimary,
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SearchTopBarPreview() {
    SmashingAndroidTheme {
        SearchTopBar(
            selectedRegion = "양천구",
            onRegionDropdownClick = {},
            onSearchClick = {},
            onRegionSelectClick = {},
            onReginItemClick = {},
            modifier = Modifier
                .background(color = colors.bgCanvas)
        )
    }
}
