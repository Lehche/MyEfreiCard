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
import androidx.compose.runtime.*
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
import com.comind.myefreicard.data.SessionManager
import com.comind.myefreicard.ui.theme.*
import kotlinx.coroutines.delay
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun AccessScreen() {
    val scrollState = rememberScrollState()

    // Live Paris time, refreshed every minute
    var now by remember { mutableStateOf(ZonedDateTime.now(ZoneId.of("Europe/Paris"))) }
    LaunchedEffect(Unit) {
        while (true) {
            now = ZonedDateTime.now(ZoneId.of("Europe/Paris"))
            delay(60_000L)
        }
    }

    val facilities = SessionManager.currentFacilities
    val openCount = facilities.count { it.isOpenAt(now) }
    val totalCount = facilities.size

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
            text = "Accès Campus",
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary
        )
        Text(
            text = "Installations autorisées — heure de Paris",
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = "Active",
                        tint = TextOnPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Statut d'accès",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextOnPrimary.copy(alpha = 0.85f)
                        )
                        Text(
                            text = "Niveau d'accès actif",
                            style = MaterialTheme.typography.headlineSmall,
                            color = TextOnPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$openCount/$totalCount",
                        style = MaterialTheme.typography.headlineLarge,
                        color = TextOnPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "ouverts maintenant",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextOnPrimary.copy(alpha = 0.8f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Group by campus
        val byCampus = facilities.groupBy { it.campus }
        byCampus.forEach { (campus, facilities) ->
            // Campus header
            Text(
                text = campus,
                style = MaterialTheme.typography.titleSmall,
                color = PrimaryBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            facilities.forEach { facility ->
                FacilityCard(facility, facility.isOpenAt(now))
                Spacer(modifier = Modifier.height(10.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

private fun Facility.isOpenAt(now: ZonedDateTime): Boolean {
    val nowMinutes = now.hour * 60 + now.minute
    val openMinutes = openHour * 60 + openMinute
    val closeMinutes = closeHour * 60 + closeMinute
    return nowMinutes in openMinutes until closeMinutes
}

private fun formatHour(hour: Int, minute: Int): String {
    return "%02dh%02d".format(hour, minute)
}

@Composable
private fun FacilityCard(facility: Facility, isOpen: Boolean) {
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${facility.building}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (isOpen) StatusActive else Color(0xFFEF4444))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isOpen) "Ouvert" else "Fermé",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isOpen) StatusActive else Color(0xFFEF4444),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "  •  ${formatHour(facility.openHour, facility.openMinute)}–${formatHour(facility.closeHour, facility.closeMinute)}",
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
        FacilityIcon.GYM -> FacilityIconData(
            Icons.Outlined.FitnessCenter,
            Color(0xFFF59E0B),
            Color(0xFFFEF3C7)
        )
        FacilityIcon.DANCE -> FacilityIconData(
            Icons.Outlined.MusicNote,
            Color(0xFFEC4899),
            Color(0xFFFCE7F3)
        )
        FacilityIcon.DINING -> FacilityIconData(
            Icons.Outlined.Restaurant,
            Color(0xFF8B5CF6),
            Color(0xFFF3EEFF)
        )
        FacilityIcon.LAB -> FacilityIconData(
            Icons.Outlined.Science,
            Color(0xFF3B82F6),
            Color(0xFFEFF6FF)
        )
        FacilityIcon.HUB -> FacilityIconData(
            Icons.Outlined.Groups,
            Color(0xFF10B981),
            Color(0xFFD1FAE5)
        )
        FacilityIcon.CAFE -> FacilityIconData(
            Icons.Outlined.LocalCafe,
            Color(0xFF92400E),
            Color(0xFFFEF3C7)
        )
    }
}
