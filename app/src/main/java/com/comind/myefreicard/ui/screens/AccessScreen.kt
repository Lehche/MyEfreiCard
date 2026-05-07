package com.comind.myefreicard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comind.myefreicard.data.Facility
import com.comind.myefreicard.data.FacilityIcon
import com.comind.myefreicard.data.SampleData
import com.comind.myefreicard.ui.theme.*

@Composable
fun AccessScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 100.dp)
    ) {
        // Title
        Text(
            text = "Facility Access",
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary
        )
        Text(
            text = "Your authorized campus facilities",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Status Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp, RoundedCornerShape(16.dp), ambientColor = GreenPrimary.copy(alpha = 0.25f)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(GreenGradientStart, GreenGradientEnd),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, 0f)
                        )
                    )
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "Active",
                    tint = TextOnPrimary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Access Status",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextOnPrimary.copy(alpha = 0.85f)
                    )
                    Text(
                        text = "All Facilities Active",
                        style = MaterialTheme.typography.headlineSmall,
                        color = TextOnPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Facility Cards
        SampleData.facilities.forEach { facility ->
            FacilityCard(facility)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun FacilityCard(facility: Facility) {
    val iconData = getFacilityIconData(facility.iconType)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(iconData.backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = iconData.icon,
                    contentDescription = facility.name,
                    tint = iconData.iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = facility.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = facility.type,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (facility.isActive) StatusActive else Color.Red)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (facility.isActive) "Active" else "Inactive",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (facility.isActive) StatusActive else Color.Red,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "  •  ${facility.hours}",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

private data class FacilityIconData(
    val icon: ImageVector,
    val iconColor: Color,
    val backgroundColor: Color
)

private fun getFacilityIconData(type: FacilityIcon): FacilityIconData {
    return when (type) {
        FacilityIcon.LIBRARY -> FacilityIconData(
            Icons.Outlined.MenuBook,
            Color(0xFF4361EE),
            Color(0xFFEEF1FF)
        )
        FacilityIcon.GYM -> FacilityIconData(
            Icons.Outlined.FitnessCenter,
            Color(0xFFF59E0B),
            Color(0xFFFEF3C7)
        )
        FacilityIcon.DINING -> FacilityIconData(
            Icons.Outlined.Restaurant,
            Color(0xFF8B5CF6),
            Color(0xFFF3EEFF)
        )
        FacilityIcon.LAB -> FacilityIconData(
            Icons.Outlined.Computer,
            Color(0xFF3B82F6),
            Color(0xFFEFF6FF)
        )
        FacilityIcon.HEALTH -> FacilityIconData(
            Icons.Outlined.FavoriteBorder,
            Color(0xFFEF4444),
            Color(0xFFFEE2E2)
        )
        FacilityIcon.AUDITORIUM -> FacilityIconData(
            Icons.Outlined.MeetingRoom,
            Color(0xFF10B981),
            Color(0xFFD1FAE5)
        )
    }
}
