package com.comind.myefreicard.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comind.myefreicard.data.ProfilesData
import com.comind.myefreicard.data.SessionManager
import com.comind.myefreicard.ui.theme.*

// Efrei brand colors for the SSO screens
private val EfreiNavy = Color(0xFF0f305e)
private val EfreiNavyLight = Color(0xFF23528b)
private val EfreiAccentBlue = Color(0xFF3b82f6)
private val EfreiGreen = Color(0xFF10b981)
private val EfreiErrorRed = Color(0xFFef4444)
private val SlateText = Color(0xFF1e293b)
private val MutedText = Color(0xFF64748b)
private val BorderColor = Color(0xFFe2e8f0)
private val BgLight = Color(0xFFF8FAFC)

@Composable
fun AuthScreen(onLoginSuccess: () -> Unit) {
    var showLoginForm by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    fun attemptLogin() {
        val inputEmail = username.trim().lowercase()
        val match = ProfilesData.profiles.find {
            it.student.email == inputEmail && it.password == password
        }
        if (match != null) {
            showError = false
            SessionManager.isLoggedIn = true
            SessionManager.loggedInEmail = match.student.email
            onLoginSuccess()
        } else {
            showError = true
        }
    }

    fun autofill(email: String, pass: String) {
        username = email
        password = pass
        showError = false
    }

    Box(modifier = Modifier.fillMaxSize().background(BgLight)) {
        AnimatedContent(
            targetState = showLoginForm,
            transitionSpec = {
                slideInHorizontally(tween(450)) { it } + fadeIn(tween(350)) togetherWith
                        slideOutHorizontally(tween(450)) { -it } + fadeOut(tween(250))
            },
            label = "auth_transition"
        ) { isLoginForm ->
            if (!isLoginForm) {
                // ─────────────────────────────────────────────
                // SCREEN 1: WELCOME SCREEN
                // ─────────────────────────────────────────────
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(EfreiNavy, EfreiNavyLight, Color(0xFF1a5276))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    ) {
                        // Dome icon circle
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🏛️", fontSize = 36.sp)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "myefrei",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontStyle = FontStyle.Italic,
                            color = Color.White,
                            letterSpacing = (-1.5).sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50.dp))
                                .background(Color.White.copy(alpha = 0.15f))
                                .padding(horizontal = 14.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "PLATEFORME WEB",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White.copy(alpha = 0.9f),
                                letterSpacing = 1.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Bienvenue sur myEfrei",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Retrouvez l'ensemble de vos services\nacadémiques, emploi du temps et carte étudiante.",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.75f),
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(36.dp))

                        // SE CONNECTER button
                        Button(
                            onClick = { showLoginForm = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .shadow(8.dp, RoundedCornerShape(14.dp)),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                        ) {
                            Text(
                                text = "SE CONNECTER",
                                color = EfreiNavy,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    // Footer
                    Text(
                        text = "© 2026 Efrei Paris. Tous droits réservés.",
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 11.sp,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 24.dp)
                    )
                }
            } else {
                // ─────────────────────────────────────────────
                // SCREEN 2: LOGIN FORM
                // ─────────────────────────────────────────────
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    // Blue header banner
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(EfreiNavy, EfreiNavyLight)
                                )
                            )
                            .padding(start = 24.dp, end = 24.dp, top = 40.dp, bottom = 24.dp)
                    ) {
                        // University logo row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("🏛️", fontSize = 18.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "efrei",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    letterSpacing = (-0.5).sp
                                )
                                Text(
                                    text = "PARIS PANTHÉON-ASSAS UNIVERSITÉ",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White.copy(alpha = 0.75f),
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }

                        Text(
                            text = "Connexion",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Utiliser votre compte Efrei unique",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.75f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    // Form body
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(24.dp)
                    ) {
                        // Error banner
                        AnimatedVisibility(
                            visible = showError,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2))
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(4.dp)
                                            .height(40.dp)
                                            .clip(RoundedCornerShape(2.dp))
                                            .background(EfreiErrorRed)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Identifiant ou mot de passe incorrect.\nVeuillez réessayer.",
                                        color = Color(0xFF991B1B),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        lineHeight = 18.sp
                                    )
                                }
                            }
                        }

                        // Username field
                        Text(
                            text = "Identifiant ou n° de dossier",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SlateText,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it; showError = false },
                            placeholder = {
                                Text("exemple@efrei.net", color = MutedText.copy(alpha = 0.5f))
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = MutedText,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = EfreiAccentBlue,
                                unfocusedBorderColor = BorderColor,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = BgLight
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Password field
                        Text(
                            text = "Mot de passe",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SlateText,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it; showError = false },
                            placeholder = {
                                Text("••••••••", color = MutedText.copy(alpha = 0.5f))
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = MutedText,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { showPassword = !showPassword }) {
                                    Icon(
                                        if (showPassword) Icons.Default.VisibilityOff
                                        else Icons.Default.Visibility,
                                        contentDescription = "Toggle password visibility",
                                        tint = MutedText,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            singleLine = true,
                            visualTransformation = if (showPassword) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = EfreiAccentBlue,
                                unfocusedBorderColor = BorderColor,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = BgLight
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    attemptLogin()
                                }
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Forgot link
                        Text(
                            text = "Identifiants oubliés ?",
                            color = EfreiAccentBlue,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(top = 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .clickable { /* no-op for demo */ }
                                .padding(4.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Submit button
                        Button(
                            onClick = { attemptLogin() },
                            colors = ButtonDefaults.buttonColors(containerColor = EfreiNavy),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                        ) {
                            Text(
                                text = "SE CONNECTER",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // ─── DEMO ACCOUNTS DRAWER ───
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = BgLight),
                            border = BorderStroke(1.5.dp, BorderColor)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(bottom = 10.dp)
                                ) {
                                    Text("⚡", fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = "COMPTES DE DÉMONSTRATION",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EfreiNavy,
                                        letterSpacing = 0.5.sp
                                    )
                                }

                                // 2x2 grid of demo buttons
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        DemoAccountButton(
                                            emoji = "🧑‍💻",
                                            name = "Gaspard Dupont",
                                            role = "Étudiant Prépa",
                                            modifier = Modifier.weight(1f),
                                            onClick = { autofill("gaspard.dupont@efrei.net", "gaspard_prepa") }
                                        )
                                        DemoAccountButton(
                                            emoji = "👩‍🏫",
                                            name = "Ada Lovelace",
                                            role = "Prof. Cyber & IA",
                                            modifier = Modifier.weight(1f),
                                            onClick = { autofill("ada.lovelace@efrei.fr", "cyber_queen_1815") }
                                        )
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        DemoAccountButton(
                                            emoji = "🕵️‍♂️",
                                            name = "Ethan Hunt",
                                            role = "Agent Secret",
                                            modifier = Modifier.weight(1f),
                                            onClick = { autofill("ethan.hunt@efrei.net", "mission_impossible") }
                                        )
                                        DemoAccountButton(
                                            emoji = "🕶️",
                                            name = "Neo (Matrix)",
                                            role = "L'Élu (The One)",
                                            modifier = Modifier.weight(1f),
                                            onClick = { autofill("neo@efrei.net", "follow_the_white_rabbit") }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Back button
                        TextButton(
                            onClick = { showLoginForm = false },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = "← Retour",
                                color = MutedText,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DemoAccountButton(
    emoji: String,
    name: String,
    role: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BorderColor)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text("$emoji $name", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SlateText)
            Text(role, fontSize = 10.sp, fontWeight = FontWeight.Normal, color = MutedText)
        }
    }
}
