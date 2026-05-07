package com.comind.myefreicard.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Nfc
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comind.myefreicard.data.SampleData
import com.comind.myefreicard.ui.theme.*

@Composable
fun CardScreen() {
    val student = SampleData.student
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Digital Student ID",
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "University Student Card",
            style = MaterialTheme.typography.bodyMedium,
            color = TextBlue,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Main Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = PrimaryBlue.copy(alpha = 0.3f),
                    spotColor = PrimaryBlue.copy(alpha = 0.3f)
                ),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(GradientStart, GradientEnd),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        )
                    )
            ) {
                // Card Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "MyEfreiCard",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        ),
                        color = TextOnPrimary
                    )
                    Text(
                        text = "Efrei | Paris Panthéon Assas Université",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextOnPrimary.copy(alpha = 0.85f)
                    )
                }

                // Student Info Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Photo placeholder
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = 36.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${student.firstName} ${student.lastName}",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextOnPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = student.program,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextOnPrimary.copy(alpha = 0.9f),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        StudentInfoRow("ID:", student.studentId)
                        StudentInfoRow("Year:", student.year)
                        StudentInfoRow("INE:", student.ine)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // NFC Scan Area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(NfcBackground.copy(alpha = 0.95f))
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        NfcPulsingButton()
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Hold near reader to verify",
                            style = MaterialTheme.typography.bodySmall,
                            color = PrimaryBlue.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Valid until
                Text(
                    text = "Valid until: ${student.validUntil}",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextOnPrimary.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom info cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoChipCard(
                label = "Enrollment Date",
                value = "Sep 2022",
                modifier = Modifier.weight(1f)
            )
            InfoChipCard(
                label = "Status",
                value = "● Active",
                valueColor = StatusActive,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StudentInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextOnPrimary.copy(alpha = 0.75f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = TextOnPrimary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun NfcPulsingButton() {
    val infiniteTransition = rememberInfiniteTransition(label = "nfc_pulse")

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 0.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    val rippleRadius by infiniteTransition.animateFloat(
        initialValue = 60f,
        targetValue = 90f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ripple"
    )

    Box(
        modifier = Modifier.size(140.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer pulsing ring
        Canvas(modifier = Modifier.size(140.dp)) {
            drawCircle(
                color = NfcCircleOuter.copy(alpha = pulseAlpha),
                radius = rippleRadius * density,
                style = Stroke(width = 2.dp.toPx())
            )
        }

        // Middle ring
        Box(
            modifier = Modifier
                .size((110 * pulseScale).dp)
                .clip(CircleShape)
                .background(NfcCircleOuter.copy(alpha = 0.25f))
        )

        // Inner circle
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(PrimaryBlue, PrimaryBlueDark)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Outlined.Smartphone,
                    contentDescription = "NFC",
                    tint = TextOnPrimary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "TAP TO SCAN",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                    color = TextOnPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun InfoChipCard(
    label: String,
    value: String,
    valueColor: Color = TextPrimary,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = valueColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

private val EaseInOutCubic: Easing = CubicBezierEasing(0.645f, 0.045f, 0.355f, 1f)
private val EaseOut: Easing = CubicBezierEasing(0f, 0f, 0.2f, 1f)
