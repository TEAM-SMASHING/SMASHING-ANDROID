package com.smashing.app.presentation.matching.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.common.type.TierType
import com.smashing.app.data.model.matching.ReceivedMatchingItem
import com.smashing.app.data.model.matching.RequesterSummary
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ReceivedMatchingCard(
    item: ReceivedMatchingItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            // 닉네임 & 성별
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = item.requesterSummary.nickname,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (item.requesterSummary.gender == "MALE") "남" else "여",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 티어 & 전적
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = item.requesterSummary.tierType?.name ?: "Unranked",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "${item.requesterSummary.winCount}승 ${item.requesterSummary.loseCount}패",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // 후기 수 & 요청 시간
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "후기 ${item.requesterSummary.reviewCount}개",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = formatDateTime(item.createdAt),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private fun formatDateTime(dateTime: OffsetDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm")
    return dateTime.format(formatter)
}

@Preview(showBackground = true)
@Composable
private fun ReceivedMatchingCardPreview() {
    ReceivedMatchingCard(
        item = ReceivedMatchingItem(
            matchingId = "matching_001",
            createdAt = OffsetDateTime.now(),
            status = "REQUESTED",
            requesterSummary = RequesterSummary(
                userId = "user_001",
                nickname = "윤서",
                gender = "FEMALE",
                tierType = TierType.SILVER_2,
                winCount = 18,
                loseCount = 7,
                reviewCount = 12,
            ),
        ),
    )
}
