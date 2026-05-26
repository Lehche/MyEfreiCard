package com.comind.myefreicard

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.comind.myefreicard.data.SessionManager
import com.comind.myefreicard.navigation.NavGraph
import com.comind.myefreicard.navigation.bottomNavItems
import com.comind.myefreicard.ui.screens.AuthScreen
import com.comind.myefreicard.ui.screens.FingerprintScreen
import com.comind.myefreicard.ui.theme.*

enum class AppState {
    UNAUTHENTICATED,
    LOCKED,
    UNLOCKED
}

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize persistent session storage
        SessionManager.init(this)
        
        setContent {
            MyEfreiCardTheme {
                // Root state machine checking session persistence and biometric preferences
                var appState by remember {
                    mutableStateOf(
                        if (!SessionManager.isLoggedIn) {
                            AppState.UNAUTHENTICATED
                        } else if (SessionManager.isBiometricsEnabled) {
                            AppState.LOCKED
                        } else {
                            AppState.UNLOCKED
                        }
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (appState) {
                        AppState.UNAUTHENTICATED -> {
                            AuthScreen(
                                onLoginSuccess = {
                                    // Dynamic unlock on successful SSO verification
                                    appState = AppState.UNLOCKED
                                }
                            )
                        }
                        AppState.LOCKED -> {
                            FingerprintScreen(
                                onUnlockSuccess = {
                                    appState = AppState.UNLOCKED
                                },
                                onLogoutTriggered = {
                                    SessionManager.logout()
                                    appState = AppState.UNAUTHENTICATED
                                }
                            )
                        }
                        AppState.UNLOCKED -> {
                            MainScreen(
                                onLogoutTriggered = {
                                    SessionManager.logout()
                                    appState = AppState.UNAUTHENTICATED
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(onLogoutTriggered: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = PrimaryBlue.copy(alpha = 0.15f),
                        spotColor = PrimaryBlue.copy(alpha = 0.1f)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
            ) {
                NavigationBar(
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp,
                    modifier = Modifier.height(72.dp)
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                        val animatedColor by animateColorAsState(
                            targetValue = if (selected) PrimaryBlue else TextTertiary,
                            animationSpec = tween(300),
                            label = "nav_color"
                        )

                        NavigationBarItem(
                            icon = {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    if (selected) {
                                        Box(
                                            modifier = Modifier
                                                .width(24.dp)
                                                .height(3.dp)
                                                .clip(RoundedCornerShape(2.dp))
                                                .background(PrimaryBlue)
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                    }
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = item.title,
                                        tint = animatedColor,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    color = animatedColor,
                                    fontSize = 11.sp,
                                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            },
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavGraph(navController = navController, onLogoutTriggered = onLogoutTriggered)
        }
    }
}
