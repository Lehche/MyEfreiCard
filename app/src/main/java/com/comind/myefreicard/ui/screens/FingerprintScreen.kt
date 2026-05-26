package com.comind.myefreicard.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.comind.myefreicard.data.SessionManager
import com.comind.myefreicard.ui.security.BiometricHelper
import com.comind.myefreicard.ui.theme.*

@Composable
fun FingerprintScreen(onUnlockSuccess: () -> Unit, onLogoutTriggered: () -> Unit) {
    val context = LocalContext.current
    val student = SessionManager.currentStudent
    val profile = SessionManager.currentProfile
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    // Automatically trigger biometric prompt when screen loads
    LaunchedEffect(Unit) {
        if (context is FragmentActivity && BiometricHelper.isBiometricAvailable(context)) {
            BiometricHelper.showBiometricPrompt(
                activity = context,
                onSuccess = { onUnlockSuccess() },
                onError = { err -> errorMessage = err }
            )
        }
    }

    val triggerBiometrics = {
        if (context is FragmentActivity && BiometricHelper.isBiometricAvailable(context)) {
            BiometricHelper.showBiometricPrompt(
                activity = context,
                onSuccess = { onUnlockSuccess() },
                onError = { err -> errorMessage = err }
            )
        } else {
            errorMessage = "Biométrie non disponible. Utiliser la simulation ci-dessous."
        }
    }

    // Compose Animation for Pulsing Ripple
    val infiniteTransition = rememberInfiniteTransition(label = "fingerprint_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PrimaryBlue, PrimaryBlueDark)
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "App Locked",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "myEfrei Sécurisé",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Application Verrouillée",
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.65f),
                fontWeight = FontWeight.Medium
            )
        }

        // Center Welcome Panel & Pulsing Fingerprint
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // User greeting
            Text(
                text = "${profile.avatarEmoji} Ravis de vous revoir,",
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${student.firstName} ${student.lastName}",
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = student.program,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 4.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Scanner Ring
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clickable { triggerBiometrics() },
                contentAlignment = Alignment.Center
            ) {
                // Animated outer pulsing canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color.White.copy(alpha = pulseAlpha),
                        radius = (size.minDimension / 2) * pulseScale,
                        style = Stroke(width = 3.dp.toPx())
                    )
                }

                // Inner static white-border circle
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.1f))
                        .shadow(8.dp, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Scan Fingerprint",
                        tint = Color.White,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Touchez le capteur pour déverrouiller",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )

            // Dynamic Error Message Banner
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = errorMessage!!,
                    color = Color(0xFFFCA5A5),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
            }
        }

        // Bottom Fallback buttons and Disconnect
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            // Emulators/Demo bypass button
            Button(
                onClick = { onUnlockSuccess() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.15f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Simulate",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Simuler l'empreinte (Demo)",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Disconnect link
            Text(
                text = "Se déconnecter / Changer de compte",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable { onLogoutTriggered() }
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
